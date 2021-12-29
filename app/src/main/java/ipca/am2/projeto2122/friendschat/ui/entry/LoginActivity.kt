package ipca.am2.projeto2122.friendschat.ui.entry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.am2.projeto2122.friendschat.MainActivity
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    private lateinit var _binding   : ActivityLoginBinding
    private lateinit var _auth      : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()

        _auth = Firebase.auth

        _binding.editTextEmailAddress

        _binding.buttonSignIn.setOnClickListener {

            val email       : String = _binding.editTextEmailAddress.text.toString()
            val password    : String = _binding.editTextPassword.text.toString()

            _auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                     if (task.isSuccessful){

                         startActivity(Intent(baseContext, MainActivity::class.java))

                    }else{

                        Toast.makeText(baseContext, "erro",Toast.LENGTH_SHORT).show()

                     }
                }
        }
    }
}