package ipca.am2.projeto2122.friendschat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

    private var _binding    : ActivityMessageChatBinding? = null

    var mChatList                   : List<Chat>?     = null
    var chatsAdapter                : ChatAdapter?    = null
    lateinit var recyclerViewChats  : RecyclerView

    var firebaseUser                : FirebaseUser?         = null
    var referenceDatabase           : DatabaseReference?    = null

    var userIdVisit                 : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMessageChatBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        userIdVisit = intent.getStringExtra("visitUser_id")

        firebaseUser = FirebaseAuth.getInstance().currentUser


        recyclerViewChats = _binding!!.recyclerViewChats
        recyclerViewChats.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recyclerViewChats.layoutManager = linearLayoutManager

        referenceDatabase = FirebaseDatabase.getInstance().reference
            .child("Users")
            .child(userIdVisit!!)

        //val toolbarChatMessenger : Toolbar = findViewById(R.id.toolbar_Message_Chat)
        val toolbarChatMessenger = _binding!!.toolbarMessageChat
        setSupportActionBar(toolbarChatMessenger)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbarChatMessenger.setNavigationOnClickListener{
            finish()
        }

        //get database reference visit user id
        referenceDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
              val visitUser : Users? = p0.getValue(Users::class.java)

                _binding!!.toolbarTextViewUsernameMChat.text = visitUser!!.getUsername()
                Picasso.get().load(visitUser.getProfile()).into(_binding!!.imageViewProfileImageMChat)

                getMessages(firebaseUser!!.uid, userIdVisit!!, visitUser.getProfile())
                
            }

            override fun onCancelled(p0: DatabaseError) {
            }


        })

        _binding?.imageViewSendMessage?.setOnClickListener {

            val sendMessage = _binding!!.textViewTextMessage.text.toString()
                if (sendMessage.isEmpty()){
                    Toast.makeText(this@MessageChatActivity, "Message is empty", Toast.LENGTH_LONG).show()
                }else{
                    sendMessageToVisitUser(firebaseUser!!.uid, userIdVisit!!, sendMessage )

                }
            _binding!!.textViewTextMessage.setText("")


        }

    }
        // Send message to intent user
    private fun sendMessageToVisitUser(senderId: String?, receiverID: String?, sendMessage: String) {
        val referenceDatabase   = FirebaseDatabase.getInstance().reference
        val keyMessage  = referenceDatabase.push().key

        val messageSendToHasMap = HashMap<String, Any?>()
        messageSendToHasMap["senderId"] = senderId
        messageSendToHasMap["senderMessage"] = sendMessage
        messageSendToHasMap["receiverMessage"] = receiverID
        messageSendToHasMap["isSeen"] = false
        messageSendToHasMap["uRl"] = ""
        messageSendToHasMap["messageID"] = keyMessage
        referenceDatabase.child("Chats").child(keyMessage!!)
            .setValue(messageSendToHasMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){

                    val chatsListReference = FirebaseDatabase.getInstance().reference
                        .child("ChatLists")
                        .child(firebaseUser!!.uid)
                        .child(userIdVisit!!)

                    chatsListReference.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(p0: DataSnapshot) {
                            if (p0.exists()){
                                chatsListReference.child("id").setValue(userIdVisit)
                            }
                            val chatsListReceiverReference = FirebaseDatabase.getInstance().reference
                                .child("ChatLists")
                                .child(userIdVisit!!)
                                .child(firebaseUser!!.uid)
                            chatsListReceiverReference.child("id").setValue(firebaseUser!!.uid)
                        }

                        override fun onCancelled(p0: DatabaseError) {
                        }

                    })
                }
            }
    }
        //Retrieve all messages from the intent user
    private fun getMessages(senderId : String?, receiverID : String?, receiverImageUrl : String?) {
        mChatList = ArrayList()
            val userReference = FirebaseDatabase.getInstance().reference.child("Chats")

            userReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    (mChatList as ArrayList<Chat>).clear()

                    for (snapshot in p0.children){
                        val chat = snapshot.getValue(Chat::class.java)

                        if (chat!!.getReceiver().equals(senderId) && chat.getSender().equals(receiverID)
                            || chat.getReceiver().equals(receiverID) && chat.getSender().equals(senderId))
                            (mChatList as ArrayList<Chat>).add(chat)
                    }
                    chatsAdapter = ChatAdapter(this@MessageChatActivity, (mChatList as ArrayList<Chat>), receiverImageUrl!!)
                    _binding!!.recyclerViewChats.adapter = chatsAdapter
                }

                override fun onCancelled(p0: DatabaseError) {}


            })

    }
}