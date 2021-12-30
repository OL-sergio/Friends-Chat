package ipca.am2.projeto2122.friendschat.ui.entry

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.databinding.ActivityRegisterBinding
import ipca.am2.projeto2122.friendschat.databinding.ActivitySplashBinding
import ipca.am2.projeto2122.friendschat.databinding.ActivityWelcomeBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityRegisterBinding

    private lateinit var _auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()

        _auth = FirebaseAuth.getInstance()



        _binding.buttonSignUpUser.setOnClickListener {

            createUser()

        }
        _binding.textViewGoToSignIn.setOnClickListener {
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createUser() {
        val username    : String = _binding.editTextUserName.text.toString()
        val phonenumber : String = _binding.editTextPhoneNumber.text.toString()
        val email       : String = _binding.editTextEmailAddress.text.toString()
        val password    : String = _binding.editTextPassword.text.toString()

        _auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){

                    val intent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(baseContext, "Error Message: " + task
                        .exception?.message.toString(),Toast.LENGTH_SHORT).show()

                }

                _binding.textViewGoToSignIn.setOnClickListener {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
    }
}