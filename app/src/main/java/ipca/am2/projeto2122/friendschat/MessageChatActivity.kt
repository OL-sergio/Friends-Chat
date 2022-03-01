package ipca.am2.projeto2122.friendschat

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
import ipca.am2.projeto2122.friendschat.ui.model.Chat
import ipca.am2.projeto2122.friendschat.ui.model.Users


class MessageChatActivity : AppCompatActivity() {

    private var _binding                    : ActivityMessageChatBinding? = null
    private var referenceDatabase           : DatabaseReference?    = null
    private var recyclerViewChats           : RecyclerView?  = null

    var mChatList                   : List<Chat>?     = null
    var chatsAdapter                : ChatAdapter?    = null
    var firebaseUser                : FirebaseUser?   = null

    var userVisitID                 : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMessageChatBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        val toolbarChatMessenger = _binding!!.toolbarMessageChat
        setSupportActionBar(toolbarChatMessenger)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbarChatMessenger.setNavigationOnClickListener{
            finish()
        }

        intent = intent
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
              val visitUser : Users? = p0.getValue(Users::class.java)

                _binding!!.toolbarTextViewUsernameMChat.text = visitUser!!.getUsername()
                Picasso.get().load(visitUser.getProfile()).into(_binding!!.imageViewProfileImageMChat)

                getMessagesSentUser(firebaseUser!!.uid, userVisitID!!, visitUser.getProfile())
                
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })

        _binding!!.imageViewSendMessage.setOnClickListener {

            val sendMessage = _binding!!.editTextTextMessage.text.toString()
                if (sendMessage.isEmpty()){
                    Toast.makeText(this@MessageChatActivity, MESSAGE_EMPTY, Toast.LENGTH_LONG).show()
                }else{
                    sendMessageToVisitUser(firebaseUser!!.uid, userVisitID!!, sendMessage )
                }
            _binding!!.editTextTextMessage.setText("")
        }

    }

        // Send message to intent user
    private fun sendMessageToVisitUser(senderID: String?, receiverID: String?, message: String) {
        val referenceDatabase   = FirebaseDatabase.getInstance().reference
        val keyMessage  = referenceDatabase.push().key

        val messageSendToHasMap = HashMap<String, Any?>()
        messageSendToHasMap["senderID"] = senderID
        messageSendToHasMap["receiverID"] = receiverID
        messageSendToHasMap["message"] = message
        messageSendToHasMap["isSeen"] = false
        messageSendToHasMap["uRl"] = ""
        messageSendToHasMap["messageID"] = keyMessage
        referenceDatabase.child(CHATS).child(keyMessage!!)
            .setValue(messageSendToHasMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){

                    val chatsListReference = FirebaseDatabase.getInstance().reference
                        .child(CHATS_LISTS)
                        .child(firebaseUser!!.uid)
                        .child(userVisitID!!)

                    chatsListReference.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(p0: DataSnapshot) {
                            if (!p0.exists()){
                                chatsListReference.child(ID).setValue(userVisitID)
                            }
                            val chatsListReceiverReference = FirebaseDatabase.getInstance().reference
                                .child(CHATS_LISTS)
                                .child(userVisitID!!)
                                .child(firebaseUser!!.uid)
                            chatsListReceiverReference.child(ID).setValue(firebaseUser!!.uid)
                        }

                        override fun onCancelled(p0: DatabaseError) {
                        }

                    })
                }
            }
    }

        //Retrieve all messages from the intent user
    private fun getMessagesSentUser(senderID : String, receiverID : String?, receiverImageUrl : String?) {
        mChatList = ArrayList()
            val userReference = FirebaseDatabase.getInstance().reference.child(CHATS)

            userReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    (mChatList as ArrayList<Chat>).clear()
                    for (snapshot in p0.children){
                        val chat = snapshot.getValue(Chat::class.java)

                        if (chat!!.getReceiverID().equals(senderID) && chat.getSenderID().equals(receiverID)
                            || chat.getReceiverID().equals(receiverID) && chat.getSenderID().equals(senderID)){

                                    (mChatList as ArrayList<Chat>).add(chat)
                        }
                        chatsAdapter = ChatAdapter(baseContext, (mChatList as ArrayList<Chat>), receiverImageUrl!!)
                        _binding!!.recyclerViewChats.adapter = chatsAdapter
                    }
                }
                override fun onCancelled(p0: DatabaseError) {

                }
            })

    }

    companion object {
        const val USERS = "Users"
        const val VISIT_USER_ID = "visitUser_id"
        const val MESSAGE_EMPTY = "Please write something"
        const val CHATS = "Chats"
        const val CHATS_LISTS = "ChatsLists"
        const val ID = "id"
    }
}