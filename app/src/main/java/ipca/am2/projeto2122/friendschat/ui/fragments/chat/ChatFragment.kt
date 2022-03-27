package ipca.am2.projeto2122.friendschat.ui.fragments.chat

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ipca.am2.projeto2122.friendschat.databinding.FragmentChatBinding

import ipca.am2.projeto2122.friendschat.adapters.UserAdapter
import ipca.am2.projeto2122.friendschat.constants.Constants.Companion.CHATS_LISTS
import ipca.am2.projeto2122.friendschat.constants.Constants.Companion.USERS
import ipca.am2.projeto2122.friendschat.model.ChatList
import ipca.am2.projeto2122.friendschat.model.Users


class ChatFragment : Fragment() {

    private var _binding : FragmentChatBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var userAdapter     : UserAdapter? = null
    private var mUsers          : List<Users>? = null
    private var mUsersChatList  : List<ChatList>? = null
    private var firebaseUser    : FirebaseUser? = null
    private lateinit var recyclerViewChatListWithMessages : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerViewChatListWithMessages = _binding!!.recyclerViewChatListMessages
        recyclerViewChatListWithMessages.setHasFixedSize(true)
        recyclerViewChatListWithMessages.layoutManager = LinearLayoutManager(context,
             LinearLayoutManager.VERTICAL, false)

        //adapter is not working
        val adapterUserAdapter = userAdapter
        binding.recyclerViewChatListMessages.adapter = adapterUserAdapter

        firebaseUser = FirebaseAuth.getInstance().currentUser
        mUsersChatList = ArrayList()

        val referenceDatabase = FirebaseDatabase.getInstance().reference
            .child(CHATS_LISTS)
            .child(firebaseUser!!.uid)

        referenceDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                (mUsersChatList as ArrayList).clear()
                for (snapShot in p0.children ){
                    val chatList = snapShot.getValue(ChatList::class.java)
                    (mUsersChatList as ArrayList).add(chatList!!)
                }
                retrieveChatList()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return root
    }

    private fun retrieveChatList() {
        mUsers = ArrayList()

        val referenceDatabase = FirebaseDatabase.getInstance().reference.child(USERS)
        referenceDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                (mUsers as ArrayList).clear()
                for (snapshot in p0.children){
                 val user = snapshot.getValue(Users::class.java)
                    for (eachChatList in mUsersChatList!!){
                     if (user!!.getUID().equals(eachChatList.getID())){
                         (mUsers as ArrayList).add(user)
                     }
                    }
                }
                userAdapter = context?.let { UserAdapter(it,(mUsers as ArrayList<Users>), true) }
                recyclerViewChatListWithMessages.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }

}