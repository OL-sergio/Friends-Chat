package ipca.am2.projeto2122.friendschat.ui.activity.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.am2.projeto2122.friendschat.MainActivity
import ipca.am2.projeto2122.friendschat.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binder : ActivitySplashBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binder.root)
        com.google.firebase.FirebaseApp.initializeApp(this)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        supportActionBar?.hide()

        if (currentUser != null) {
            navigateToActivity(MainActivity::class.java)
        } else {
            navigateToActivity(WelcomeActivity::class.java)
        }

    }
    private fun navigateToActivity(targetActivity: Class<*>) {
        lifecycleScope.launch {
            delay(3000)
            val intent = Intent(baseContext, targetActivity)
            startActivity(intent)
            finish()
        }
    }
}