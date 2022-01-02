package ipca.am2.projeto2122.friendschat.ui.entry

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.databinding.ActivityRegisterBinding

import ipca.am2.projeto2122.friendschat.ui.intro.WelcomeActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityRegisterBinding

    private lateinit var _auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val toolbar : (Toolbar) = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)

        supportActionBar!!.title = "Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener{
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()

        }


        _auth = FirebaseAuth.getInstance()

        _binding.textViewGoToSignIn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        _binding.buttonSignUpUser.setOnClickListener {

            createUser()

        }

    }

    private fun createUser() {
        val username                : String = _binding.editTextUserName.text.toString()
        val phonenumber             : String = _binding.editTextPhoneNumber.text.toString()
        val email                   : String = _binding.editTextEmailAddress.text.toString()
        val password                : String = _binding.editTextPassword.text.toString()
        val passwordConfirmation    : String = _binding.editTextConfirmPassword.text.toString()


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
            }
    }
}