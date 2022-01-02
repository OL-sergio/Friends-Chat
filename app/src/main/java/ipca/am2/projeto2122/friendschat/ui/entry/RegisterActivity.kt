package ipca.am2.projeto2122.friendschat.ui.entry

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.databinding.ActivityRegisterBinding
import ipca.am2.projeto2122.friendschat.ui.intro.WelcomeActivity
import ipca.am2.projeto2122.friendschat.ui.security.PasswordStrength
import ipca.am2.projeto2122.friendschat.ui.security.StrengthLevel

import java.util.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityRegisterBinding
    private lateinit var _auth: FirebaseAuth

    private var color: Int = R.color.weak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _auth = FirebaseAuth.getInstance()

        val passwordStrengthCalculator = PasswordStrength()
        _binding.editTextPassword.addTextChangedListener(passwordStrengthCalculator)

        val toolbar: (Toolbar) = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)

        supportActionBar!!.title = "Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        _binding.textViewGoToSignIn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        _binding.buttonSignUpUser.setOnClickListener {

            createUser()

        }


        passwordStrengthCalculator.strengthLevel.observe(this, Observer { strengthLevel ->
            displayStrengthLevel(strengthLevel)
        })
        passwordStrengthCalculator.strengthColor.observe(this, Observer { strengthColor ->
            color = strengthColor
        })

    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel) {
        _binding.buttonSignUpUser.isEnabled = strengthLevel == StrengthLevel.VeryStrong

        _binding.textViewStrengthLevel.text = strengthLevel.name
        _binding.textViewStrengthLevel.setTextColor(ContextCompat.getColor(this, color))
        _binding.imageViewStrengthLevel.setColorFilter(ContextCompat.getColor(this, color))

    }

    private fun createUser() {

        val username: String = _binding.editTextUserName.text.toString()
        val phonenumber: String = _binding.editTextPhoneNumber.text.toString()
        val email: String = _binding.editTextEmailAddress.text.toString()
        val password: String = _binding.editTextPassword.text.toString()
        val passwordConfirmation: String = _binding.editTextConfirmPassword.text.toString()

        when {
            email == "" -> {

                Toast.makeText(this@RegisterActivity, "Please write Email.", Toast.LENGTH_SHORT)
                    .show()
            }
            password == "" -> {

                Toast.makeText(this@RegisterActivity, "Please write Password.", Toast.LENGTH_SHORT)
                    .show()

            } else -> {
                _auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val intent = Intent(baseContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext, "Error Message: " + task
                                    .exception?.message.toString(), Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
            }
        }

    }
}