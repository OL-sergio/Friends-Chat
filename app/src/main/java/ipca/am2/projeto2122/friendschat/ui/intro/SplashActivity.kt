package ipca.am2.projeto2122.friendschat.ui.intro

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.*
import com.google.firebase.ktx.Firebase
import ipca.am2.projeto2122.friendschat.MainActivity
import ipca.am2.projeto2122.friendschat.databinding.ActivitySplashBinding
import kotlinx.coroutines.*




@SuppressLint("CustomSplashScreen")
@DelicateCoroutinesApi
class SplashActivity : AppCompatActivity() {

    private lateinit var _binder    : ActivitySplashBinding
    private lateinit var _auth      : FirebaseAuth

    public override fun onStart() {
       super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binder = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_binder.root)

        _auth = Firebase.auth
        
        val currentUser = _auth.currentUser

        supportActionBar?.hide()

       if (currentUser != null) {
            GlobalScope.launch(Dispatchers.Main) {
                Dispatchers.Main
                delay(3000)
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                finish()

            }

        } else {
            GlobalScope.launch(Dispatchers.Main) {
                Dispatchers.Main
                delay(3000)
                val intent = Intent(baseContext, WelcomeActivity::class.java)
                startActivity(intent)
                finish()

            }
        }
    }
}