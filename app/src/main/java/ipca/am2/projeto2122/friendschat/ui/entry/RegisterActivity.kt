package ipca.am2.projeto2122.friendschat.ui.entry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.databinding.ActivitySplashBinding
import ipca.am2.projeto2122.friendschat.databinding.ActivityWelcomeBinding

class RegisterActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        supportActionBar?.hide()

    }
}