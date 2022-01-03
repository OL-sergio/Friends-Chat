package ipca.am2.projeto2122.friendschat.ui.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ipca.am2.projeto2122.friendschat.databinding.FragmentSettingsBinding
import java.io.ByteArrayOutputStream
import java.util.*

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        _binding!!.imageViewSettingsUserProfile.setOnClickListener {

            val baos = ByteArrayOutputStream()

            bitmap?.compress(Bitmap.CompressFormat.JPEG,50,baos)
            val data = baos.toByteArray()

            val storage     = Firebase.storage
            val storageReference = storage.reference
            val fileName    = "${UUID.randomUUID()}.jpg"
            val createImageReference = storageReference
                .child("ImageUserProfile/${Firebase.auth.currentUser?.uid}/$fileName")

            val uploadTask = createImageReference.putBytes(data)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful){
                    task.exception?.let {
                        throw  it
                    }
                }
                createImageReference.downloadUrl

            }
            uploadTask.addOnFailureListener{


            }.addOnSuccessListener { task ->
                storageReference
                    .child("ImageUserProfile/${Firebase.auth.currentUser?.uid}/$fileName")
                    .downloadUrl.addOnSuccessListener {
                        mDataBase.collection("ImageUserProfile")

                }


            }
        }

        _binding!!.imageViewSettingsUserProfile.setOnClickListener {
            val takePictureInt = Intent (MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureInt, CAMARA_PIC_REQUEST )

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK){
            when(requestCode) {
                CAMARA_PIC_REQUEST ->{
                    val bm : Bitmap = data!!.extras!!.get("data") as Bitmap
                    bm.let {
                        binding.imageViewSettingsUserProfile.setImageBitmap(it)
                        bitmap = bm
                    }
                }
            }
        }
    }

    companion object {
        const val CAMARA_PIC_REQUEST = 1001
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}