package ipca.am2.projeto2122.friendschat.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    var mChatList : List<Chat>? = null

    var firebaseUser            : FirebaseUser?         = null
    var chatsAdapter            : ChatAdapter?          = null
    var referenceDatabase       : DatabaseReference?    = null

    var userIdVisit     : String? = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMessageChatBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

      val toolbarChatMessenger  = _binding!!.toolbarMessageChat
        setSupportActionBar(toolbarChatMessenger)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbarChatMessenger.setNavigationOnClickListener{
            finish()
        }


        firebaseUser = FirebaseAuth.getInstance().currentUser

        referenceDatabase = FirebaseDatabase.getInstance().reference
            .child("Users")
            .child(userIdVisit!!)

        //get database reference visit user id
        referenceDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
              val visitUser : Users? = p0.getValue(Users::class.java)

                _binding!!.toolbarTextViewUsernameMChat.text = visitUser!!.getUsername()
                //Picasso.get().load(visitUser.getProfile()).into(_binding!!.imageViewProfileImageMChat)

                getMessagesToUser(firebaseUser!!.uid, userIdVisit!!, visitUser.getProfile())
                
            }

            override fun onCancelled(p0: DatabaseError) {
            }


        })

        _binding!!.imageViewSendMessage.setOnClickListener {

            val sendMessage = _binding!!.textViewTextMessage.text.toString()
                if (sendMessage.isEmpty()){
                    Toast.makeText(this@MessageChatActivity, "", Toast.LENGTH_LONG).show()
                }else{
                    sendMessageToVisitUser(firebaseUser!!.uid, userIdVisit!!, sendMessage )

                }
            _binding!!.textViewTextMessage.setText("")


        }

    }
        // Send message to intent user
    private fun sendMessageToVisitUser(senderId: String, receiverID: String, sendMessage: String) {
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
        //Retrieve all messages sent intent user
    private fun getMessagesToUser(uid: String, userIdVisit: String, profile: String?) {

    }
}