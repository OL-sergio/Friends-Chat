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
import ipca.am2.projeto2122.friendschat.ui.intro.WelcomeActivity
import ipca.am2.projeto2122.friendschat.ui.model.PrefEmail
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.EMPTY_STRING
import ipca.am2.projeto2122.friendschat.ui.Constants.Companion.TAG


class LoginActivity : AppCompatActivity() {


    private lateinit var _binding: ActivityLoginBinding
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val toolbarLogin = _binding.toolbarLogin
        setSupportActionBar(toolbarLogin)

        supportActionBar!!.title = "Login"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbarLogin.setNavigationOnClickListener {
            val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        _auth = Firebase.auth

        _binding.editTextEmailAddress.setText(Preference(this).longinPrefer)



        _binding.buttonSignIn.setOnClickListener {

            val email       : String = _binding.editTextEmailAddress.text.toString()
            val password    : String = _binding.editTextPassword.text.toString()

            when {
                email == EMPTY_STRING -> {

                    Toast.makeText(this@LoginActivity, "Please write Email.", Toast.LENGTH_SHORT)
                        .show()
                }
                password == EMPTY_STRING -> {

                    Toast.makeText(this@LoginActivity, "Please write Password.", Toast.LENGTH_SHORT)
                        .show()

                }
                else -> {
                    _auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {

                                Preference(this).longinPrefer = email
                                val emailPref = PrefEmail()

                                val database = Firebase.firestore

                                startActivity(Intent(baseContext, MainActivity::class.java))

                                database.collection("user")
                                    .add(emailPref.setEmailHash())
                                    .addOnSuccessListener { documentReference ->

                                        Log.d(TAG,"DocumentSnapShot added with ID: ${documentReference.id}"
                                        )

                                    }.addOnFailureListener { e ->

                                        Log.w(TAG,"Error adding Document", e)

                                    }

                            } else {

                                Toast.makeText(baseContext, "Invalid Username or Password", Toast.LENGTH_SHORT).show()

                            }
                        }
                }
            }
        }

        _binding.textViewGoToSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}