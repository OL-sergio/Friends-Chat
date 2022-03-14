package ipca.am2.projeto2122.friendschat.ui.fragments.settings

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.Intent.ACTION_PICK
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import io.grpc.Context
import ipca.am2.projeto2122.friendschat.databinding.FragmentSettingsBinding
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.COVER
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.EMPTY_STRING
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.IMAGE_USER
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.PROFILE
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.USERS
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.USERS_REFERENCE


import ipca.am2.projeto2122.friendschat.ui.model.Users


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var imageUri : Uri? = null
    private val RequestCode = 438
    private var coverChecker : String = EMPTY_STRING



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
        referenceStorage    = FirebaseStorage.getInstance().reference.child(USERS_REFERENCE)

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
                        Picasso.get().load(user.getCover()).into(viewBackCover)
                        Picasso.get().load(user.getProfile()).into(viewImageProfile)

                   }
               }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        viewBackCover.setOnClickListener {
            pickImage()

        }

        viewImageProfile.setOnClickListener {
            coverChecker = COVER
            pickImage()

        }

        return root

    }



    private fun pickImage() {
       val intent = Intent(Intent.ACTION_PICK)

        startActivityForResult(intent, RequestCode)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode,  data)

        if (RequestCode == requestCode && resultCode == Activity.RESULT_OK && data!!.data != null){
            imageUri = data.data
            Toast.makeText(context, "Uploading Image...", Toast.LENGTH_LONG).show()
            uploadImageToDatabase()

        }
    }

    private fun uploadImageToDatabase() {
        if (imageUri != null){
            val fileReference = referenceStorage!!.child(System
                .currentTimeMillis().toString() + ".png")

            val uploadTask : StorageTask<*>
            uploadTask = fileReference.putFile(imageUri!!)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                if (!task.isSuccessful){
                 task.exception!!.let{
                     throw it
                 }
                }
                return@Continuation fileReference.downloadUrl

            }).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val downLoadUrl = task.result
                    val mUrl = downLoadUrl.toString()

                    if (coverChecker == COVER){
                        val mapCoverImage = HashMap<String, Any>()
                        mapCoverImage[COVER] = mUrl
                        userReferenceDatabase!!.updateChildren(mapCoverImage)
                        coverChecker = EMPTY_STRING

                    } else {
                        val mapProfileImage  = HashMap<String, Any>()
                        mapProfileImage[PROFILE] = mUrl
                        userReferenceDatabase!!.updateChildren(mapProfileImage)
                        coverChecker = EMPTY_STRING
                    }
                }

            }

        }
    }
}

