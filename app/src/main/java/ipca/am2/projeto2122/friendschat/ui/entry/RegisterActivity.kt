package ipca.am2.projeto2122.friendschat.ui.entry

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.databinding.ActivityRegisterBinding
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.COVER
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.COVER_URL
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.EMAIL
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.FIREBASE_UID
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.FULL_NAME
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.PHONE_NUMBER
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.PROFILE
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.PROFILE_URL
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.SEARCH_NAME
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.STATUS
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.STATUS_OFFLINE
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.USERS
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.USER_NAME
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

        val toolbarRegister = _binding.toolbarRegister
        setSupportActionBar(toolbarRegister)

        supportActionBar!!.title = "Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbarRegister.setNavigationOnClickListener {
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
                    refUsers = FirebaseDatabase.getInstance().reference.child(USERS)
                        .child(firebaseUserID)

                    val userHashMap = HashMap<String, Any>()
                    userHashMap[FIREBASE_UID] = firebaseUserID
                    userHashMap[USER_NAME] = userName
                    userHashMap[PHONE_NUMBER] = phoneNumber
                    userHashMap[FULL_NAME] = fullName
                    userHashMap[EMAIL] = email
                    userHashMap[PROFILE] = PROFILE_URL
                    userHashMap[COVER] = COVER_URL
                    userHashMap[STATUS] = STATUS_OFFLINE
                    userHashMap[SEARCH_NAME] = userName.lowercase(Locale.getDefault())

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

