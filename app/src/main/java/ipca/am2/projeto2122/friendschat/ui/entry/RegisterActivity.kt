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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.databinding.ActivityRegisterBinding
import ipca.am2.projeto2122.friendschat.ui.intro.WelcomeActivity
import ipca.am2.projeto2122.friendschat.ui.security.PasswordStrength
import ipca.am2.projeto2122.friendschat.ui.security.StrengthLevel

import java.util.*
import kotlin.collections.HashMap


class RegisterActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID: String = ""


    private var color: Int = R.color.weak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        mAuth = FirebaseAuth.getInstance()

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

        mAuth = FirebaseAuth.getInstance()

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
        val userName                : String = _binding.editTextUserName.text.toString()
        val fullName                : String = _binding.editTextFullName.text.toString()
        val phoneNumber             : String = _binding.editTextPhoneNumber.text.toString()
        val email                   : String = _binding.editTextEmailAddress.text.toString()
        val password                : String = _binding.editTextPassword.text.toString()
        val passwordConfirmation    : String = _binding.editTextConfirmPassword.text.toString()
        //Confirm if variables was Strings
        if (email.isBlank() || password.isBlank() || passwordConfirmation.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        if (userName.isBlank() || phoneNumber.isBlank() || fullName.isBlank()) {
            Toast.makeText(this, "User, Full Name or Phone Number can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        // password confirmation
        if (password != passwordConfirmation) {
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUserID = mAuth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference
                        .child("Users")
                        .child(firebaseUserID)

                    val userHashMap = HashMap<String, Any>()
                    userHashMap["UID"] = firebaseUserID
                    userHashMap["USERNAME"] = userName
                    userHashMap["PHONENUMBER"] = phoneNumber
                    userHashMap["FULLNAME"] = fullName
                    userHashMap["PROFILE"] = ""
                    userHashMap["COVER"] = ""
                    userHashMap["STATUS"] = "offline"
                    userHashMap["SEARCH"] = userName.toLowerCase()

                    refUsers.updateChildren(userHashMap)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(baseContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else {
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

