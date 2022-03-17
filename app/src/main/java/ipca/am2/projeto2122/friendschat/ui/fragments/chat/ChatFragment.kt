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
import com.google.firebase.ktx.Firebase
import ipca.am2.projeto2122.friendschat.databinding.FragmentChatBinding
import ipca.am2.projeto2122.friendschat.ui.Adapter.UserAdapter
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.CHATS_LISTS
import ipca.am2.projeto2122.friendschat.ui.model.ChatList
import ipca.am2.projeto2122.friendschat.ui.model.Users


class ChatFragment : Fragment() {

    private var _binding : FragmentChatBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var mUserAdapter    : UserAdapter? = null
    private var mUsers          : List<Users>? = null
    private var mUsersChatList  : List<ChatList>? = null
    private var mFirebaseUser   : FirebaseUser? = null
    lateinit var recyclerViewListChat : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerViewListChat = _binding!!.recyclerViewChatListMessages
        recyclerViewListChat.setHasFixedSize(true)
        recyclerViewListChat.layoutManager = LinearLayoutManager(context)

        mFirebaseUser = FirebaseAuth.getInstance().currentUser
        mUsersChatList = ArrayList()

        val referenceDatabase = FirebaseDatabase.getInstance().reference
            .child(CHATS_LISTS)
            .child(mFirebaseUser!!.uid)

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

    }

}