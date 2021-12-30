package ipca.am2.projeto2122.friendschat.ui.entry

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.am2.projeto2122.friendschat.MainActivity
import ipca.am2.projeto2122.friendschat.databinding.ActivityLoginBinding
import ipca.am2.projeto2122.friendschat.ui.database.Preference
import ipca.am2.projeto2122.friendschat.ui.model.Users
import ipca.am2.projeto2122.friendschat.ui.search.SearchFragment

class LoginActivity : AppCompatActivity() {


    private lateinit var _binding: ActivityLoginBinding
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()

        _auth = Firebase.auth

        _binding.editTextEmailAddress.setText(Preference(this).longinPrefer)

        _binding.buttonSignIn.setOnClickListener {

            val email: String = _binding.editTextEmailAddress.text.toString()
            val password: String = _binding.editTextPassword.text.toString()

            _auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Preference(this).longinPrefer = email
                        val user = Users(email)
                        val database = Firebase.firestore

                        startActivity(Intent(baseContext, MainActivity::class.java))

                        database.collection("user")
                            .add(user.toHash())
                            .addOnSuccessListener { documentReference ->

                                Log.d(SearchFragment.TAG, "DocumentSnapShot added with ID: ${documentReference.id}")

                            }.addOnFailureListener { e ->

                                Log.w(SearchFragment.TAG, "Error adding Document", e)

                            }

                    } else {

                        Toast.makeText(baseContext, "erro", Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }
}