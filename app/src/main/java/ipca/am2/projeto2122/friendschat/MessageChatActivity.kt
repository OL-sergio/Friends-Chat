package ipca.am2.projeto2122.friendschat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import ipca.am2.projeto2122.friendschat.databinding.ActivityMessageChatBinding
import ipca.am2.projeto2122.friendschat.ui.Adapter.ChatAdapter
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.CHATS
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.CHATS_LISTS
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.EMPTY_STRING
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.ID
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.IS_SEEN
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.MESSAGE
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.MESSAGE_EMPTY
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.MESSAGE_ID
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.RECEIVER_ID
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.SENDER_ID
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.URL
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.USERS
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.VISIT_USER_ID
import ipca.am2.projeto2122.friendschat.ui.model.Chat
import ipca.am2.projeto2122.friendschat.ui.model.Users


class MessageChatActivity : AppCompatActivity() {

    private var _binding: ActivityMessageChatBinding? = null
    private var referenceDatabase: DatabaseReference? = null
    private lateinit var recyclerViewChats: RecyclerView

    var mChatList: List<Chat>? = null
    var chatsAdapter: ChatAdapter? = null
    var firebaseUser: FirebaseUser? = null

    var userVisitID: String? = EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMessageChatBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        intent = intent

        val toolbarChatMessenger = _binding!!.toolbarMessageChat
        setSupportActionBar(toolbarChatMessenger)
        supportActionBar!!.title = EMPTY_STRING
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbarChatMessenger.setNavigationOnClickListener {
            finish()
        }

        userVisitID = intent.getStringExtra(VISIT_USER_ID)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        recyclerViewChats = _binding!!.recyclerViewChats
        recyclerViewChats!!.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        // linearLayoutManager.stackFromEnd = true
        recyclerViewChats!!.layoutManager = linearLayoutManager

        val adapterChatAdapter = chatsAdapter
        _binding!!.recyclerViewChats.adapter = adapterChatAdapter

        referenceDatabase = FirebaseDatabase.getInstance().reference
            .child(USERS)
            .child(userVisitID!!)

        //val toolbarChatMessenger : Toolbar = findViewById(R.id.toolbar_Message_Chat)

        //get database reference visit user id
        referenceDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val visitUser: Users? = p0.getValue(Users::class.java)

                _binding!!.toolbarTextViewUsernameMChat.text = visitUser!!.getUsername()
                Picasso.get().load(visitUser.getProfile())
                    .into(_binding!!.imageViewProfileImageMChat)

                getMessagesSentUser(firebaseUser!!.uid, userVisitID!!, visitUser.getProfile())

            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })

        _binding!!.imageViewSendMessage.setOnClickListener {

            val sendMessage = _binding!!.editTextTextMessage.text.toString()
            if (sendMessage.isEmpty()) {
                Toast.makeText(this@MessageChatActivity, MESSAGE_EMPTY, Toast.LENGTH_LONG).show()
            } else {
                sendMessageToVisitUser(firebaseUser!!.uid, userVisitID!!, sendMessage)
            }
            _binding!!.editTextTextMessage.setText("")
        }

    }

    // Send message to intent user
    private fun sendMessageToVisitUser(senderId: String, receiverId: String?, message: String) {
        val referenceDatabase = FirebaseDatabase.getInstance().reference
        val keyMessage = referenceDatabase.push().key

        val messageSendToHashMap = HashMap<String, Any?>()
        messageSendToHashMap[SENDER_ID] = senderId
        messageSendToHashMap[RECEIVER_ID] = receiverId
        messageSendToHashMap[MESSAGE] = message
        messageSendToHashMap[IS_SEEN] = false
        messageSendToHashMap[URL] = EMPTY_STRING
        messageSendToHashMap[MESSAGE_ID] = keyMessage
        referenceDatabase.child(CHATS).child(keyMessage!!)
            .setValue(messageSendToHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val chatsListReference = FirebaseDatabase.getInstance().reference
                        .child(CHATS_LISTS)
                        .child(firebaseUser!!.uid)
                        .child(userVisitID!!)

                    chatsListReference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            if (!p0.exists()) {
                                chatsListReference.child(ID).setValue(userVisitID)
                            }
                            val chatsListReceiverReference =
                                FirebaseDatabase.getInstance().reference
                                    .child(CHATS_LISTS)
                                    .child(userVisitID!!)
                                    .child(firebaseUser!!.uid)
                            chatsListReceiverReference.child(ID).setValue(firebaseUser!!.uid)
                        }

                        override fun onCancelled(p0: DatabaseError) {
                        }

                    })
                    val referenceDatabase = FirebaseDatabase.getInstance().reference.child(USERS)
                        .child(firebaseUser!!.uid)
                }
            }

    }

    //Retrieve all messages from the intent user
    private fun getMessagesSentUser(
        senderId: String,
        receiverId: String?,
        receiverImageUrl: String?
    ) {
        mChatList = ArrayList()
        val userReference = FirebaseDatabase.getInstance().reference.child(CHATS)

        userReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                (mChatList as ArrayList<Chat>).clear()
                for (snapshot in p0.children) {
                    val chat = snapshot.getValue(Chat::class.java)

                    if ((chat!!.getReceiverID() == senderId && chat.getSenderID() == receiverId) ||
                        (chat.getReceiverID() == receiverId && chat.getSenderID() == senderId)
                    ) {

                        (mChatList as ArrayList<Chat>).add(chat)
                    }
                    chatsAdapter = ChatAdapter(baseContext!!, (mChatList!! as ArrayList<Chat>),
                        receiverImageUrl!!)
                    _binding!!.recyclerViewChats.adapter = chatsAdapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }
}
