package ipca.am2.projeto2122.friendschat.ui.fragments.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ipca.am2.projeto2122.friendschat.databinding.FragmentSearchBinding
import ipca.am2.projeto2122.friendschat.ui.Adapters.UserAdapter
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.EMPTY_STRING
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.SEARCH
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.USERS
import ipca.am2.projeto2122.friendschat.ui.model.Users
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {

    private var _binding            : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var recyclerView        : RecyclerView?          = null
    private var searchUserEditText  : EditText?              = null
    private var mUsers              : List<Users>?           = null
    private var userAdapter         : UserAdapter?           = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view: View = binding.root

        recyclerView = _binding!!.recyclerviewSearchList
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)

        //adapter is not working
        val adapterUserAdapter = userAdapter
        binding.recyclerviewSearchList.adapter = adapterUserAdapter
        
        searchUserEditText = _binding!!.editTextSearchUsers

        mUsers = ArrayList()
        retrieveAllUsers()

        searchUserEditText!!.addTextChangedListener (object : TextWatcher {
           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

           override fun afterTextChanged(s: Editable?) {}


           override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
               searchForUser(cs.toString().lowercase(Locale.getDefault()))
           }
       })

        return view
    }
     // Find users with string that are register on the data database.
    private fun searchForUser( search: String) {

        val firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid

        val queryUsers = FirebaseDatabase.getInstance().reference
            .child(USERS).orderByChild(SEARCH)
            .startAt(search)
            .endAt(search + "\uf8ff")

        queryUsers.addValueEventListener(object : ValueEventListener{

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                (mUsers as ArrayList<Users>).clear()
                for (snapshot in p0.children ){

                 val user : Users? = snapshot.getValue(Users::class.java)
                 if (!(user!!.getUID()).equals(firebaseUserID)){
                     (mUsers as ArrayList<Users>).add(user)
                    }
                 }
                userAdapter = UserAdapter(context!!, mUsers!!, false)
                recyclerView!!.adapter = userAdapter
            }
        })
    }

    // Gets all Users that are register on the Database.
    private fun retrieveAllUsers() {
        val firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid

        val referenceUsers = FirebaseDatabase.getInstance().reference.child(USERS)

        referenceUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                (mUsers as ArrayList<Users>).clear()
                if (searchUserEditText!!.text.toString() == EMPTY_STRING){
                    for (snapshot in p0.children){
                        val user: Users? = snapshot.getValue(Users::class.java)
                        if(!(user!!.getUID()).equals(firebaseUserID)){
                            (mUsers as ArrayList<Users>).add(user)
                        }
                    }
                    userAdapter = context?.let { UserAdapter(it, mUsers!!, false) }
                    binding.recyclerviewSearchList.adapter = userAdapter
                }

            }
            override fun onCancelled(p0: DatabaseError) {


            }

        })

    }

}