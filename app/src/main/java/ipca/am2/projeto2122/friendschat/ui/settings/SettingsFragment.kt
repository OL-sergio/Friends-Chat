package ipca.am2.projeto2122.friendschat.ui.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ipca.am2.projeto2122.friendschat.databinding.FragmentSettingsBinding
import ipca.am2.projeto2122.friendschat.ui.model.Users
import java.io.ByteArrayOutputStream
import java.util.*


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var bitmap : Bitmap? = null
    private var mDataBase = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.extendedFabSavePhoto.setOnClickListener {

            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val storage = Firebase.storage
            val storageReference = storage.reference
            val fileName = "${UUID.randomUUID()}.jpg"
            val createImageReference = storageReference
                .child("imageUserProfile/${Firebase.auth.currentUser?.uid}/$fileName")

            val uploadTask = createImageReference.putBytes(data)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw  it
                    }
                }
                createImageReference.downloadUrl

            }
            uploadTask.addOnFailureListener {


            }.addOnSuccessListener { task ->
                storageReference
                    .child("imageUserProfile/${Firebase.auth.currentUser?.uid}/$fileName")
                    .downloadUrl.addOnSuccessListener {

                        val downloadUri = it.toString()

                        Log.d(TAG, "DocumentSnapshot added with ID: $downloadUri")
                        val user = Users(
                            "",
                            downloadUri
                        )

                        mDataBase.collection("imageUserProfile")
                            .add(user.toHash())
                            .addOnSuccessListener { documentReference ->

                                Log.d(
                                    TAG,
                                    "DocumentSnapshot added with ID: ${documentReference.id}"
                                )

                            }.addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)

                            }
                    }.addOnFailureListener {

                    }
            }
        }



            binding.imageViewSettingsUserProfile.setOnClickListener {
            val takePictureInt = Intent (MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult( Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                CAMARA_PIC_REQUEST )

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode) {
                CAMARA_PIC_REQUEST -> {
                    val bm : Bitmap? = data?.extras?.get("data") as? Bitmap?
                    bm?.let {
                        binding.imageViewSettingsUserProfile.setImageBitmap(it)
                        bitmap = bm

                    }
                }
            }
        }
    }

    companion object {
        const val CAMARA_PIC_REQUEST = 1001
        const val TAG = "SearchFragment"

    }

}