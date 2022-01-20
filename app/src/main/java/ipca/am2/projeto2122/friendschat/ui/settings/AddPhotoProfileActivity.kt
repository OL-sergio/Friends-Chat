package ipca.am2.projeto2122.friendschat.ui.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ipca.am2.projeto2122.friendschat.databinding.ActivityAddPhotoProfileBinding
import ipca.am2.projeto2122.friendschat.ui.model.Photo
import java.io.ByteArrayOutputStream
import java.util.*


class AddPhotoProfileActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityAddPhotoProfileBinding

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var bitmap : Bitmap? = null
    private var mDataBase = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddPhotoProfileBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.extendedFabSavePhoto.visibility = View.GONE
        _binding.extendedFabSavePhoto.setOnClickListener {

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
                        val photos = Photo(
                            _binding.textViewPictureAddDescription.text.toString(),
                            downloadUri
                        )

                        mDataBase.collection("imageUserProfile")
                            .add(photos.toHash())
                            .addOnSuccessListener { documentReference ->

                                Log.d(
                                    TAG,
                                    "DocumentSnapshot added with ID: ${documentReference.id}")

                            }.addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)

                            }

                        if (savedInstanceState == null) {
                            val f = SettingsFragment()
                            val t: FragmentTransaction = supportFragmentManager.beginTransaction()
                            t.replace(_binding.extendedFabSavePhoto.id, f).commit()
                        }

                    }.addOnFailureListener {

                    }

            }

        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    handleCameraImage(result.data)

                }
            }

        _binding.imageViewAddProfileFullView.setOnClickListener {

            //intent to open camera app
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)

        }
    }


    private fun handleCameraImage(intent: Intent?) {

        val bm: Bitmap? = intent?.extras?.get("data") as? Bitmap

        bm?.let {
            _binding.imageViewAddProfileFullView.setImageBitmap(it)
            _binding.extendedFabSavePhoto.visibility = View.VISIBLE
            bitmap = bm
        }
    }

    companion object {
        const val TAG = "AddPhotoProfileActivity"

    }
}