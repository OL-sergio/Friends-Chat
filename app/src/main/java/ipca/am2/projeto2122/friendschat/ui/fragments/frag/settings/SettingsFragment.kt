package ipca.am2.projeto2122.friendschat.ui.fragments.frag.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.databinding.FragmentSettingsBinding
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.IMAGE_USER_PROFILE
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.TAG
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.USERS
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.USER_IMAGES
import ipca.am2.projeto2122.friendschat.ui.model.Photo
import ipca.am2.projeto2122.friendschat.ui.model.Users


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    var firebaseCurrentUser         : FirebaseUser?      = null
    var userReferenceDatabase       : DatabaseReference? = null
    var referenceStorage            : StorageReference?  = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firebaseCurrentUser = FirebaseAuth.getInstance().currentUser
        userReferenceDatabase   = FirebaseDatabase.getInstance().reference.child(USERS).child(firebaseCurrentUser!!.uid)
        referenceStorage    = FirebaseStorage.getInstance().reference.child(USER_IMAGES)

        val viewUserName        = _binding!!.textViewSettingsUserName
        val viewFullName        = _binding!!.textViewFullName
        val viewPhoneNumber     = _binding!!.textViewPhoneNumber
        val viewUserEmail       = _binding!!.textViewEmail
        val viewBackCover       = _binding!!.imageViewSettingsBackground
        val viewImageProfile    = _binding!!.imageViewSettingsUserProfile

        userReferenceDatabase!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()){
                   val user: Users? = snapshot.getValue(Users::class.java)

                   if (context != null){
                        viewUserName.text = user!!.getUsername()
                        viewFullName.text = user.getFullName()
                        viewPhoneNumber.text = user.getPhoneNumber()
                        viewUserEmail.text = user.getEmail()

                        //error on background cover image
                        //Picasso.get().load(user.getCover()).into(viewBackCover)
                        Picasso.get().load(user.getProfile()).into(viewImageProfile)

                   }
               }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        viewBackCover.setOnClickListener {
            pickBackgroundImage()

        }

        viewImageProfile.setOnClickListener {
           //coverChecker = "cover"
            pickImageCover()

        }

        return root

    }



    private fun pickBackgroundImage() {
        TODO("Not yet implemented")
    }

    private fun pickImageCover() {
        TODO("Not yet implemented")
    }
}

