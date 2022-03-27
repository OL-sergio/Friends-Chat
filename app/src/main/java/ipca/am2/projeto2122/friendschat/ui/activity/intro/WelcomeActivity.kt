package ipca.am2.projeto2122.friendschat.ui.activity.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ipca.am2.projeto2122.friendschat.databinding.ActivityWelcomeBinding
import ipca.am2.projeto2122.friendschat.ui.activity.entry.LoginActivity
import ipca.am2.projeto2122.friendschat.ui.activity.entry.RegisterActivity


class WelcomeActivity : AppCompatActivity() {

    private lateinit var _binder    : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binder = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(_binder.root)

        supportActionBar?.hide()


        _binder.buttonSignUpGoToLogin.setOnClickListener {

            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        _binder.buttonSignUpGoToRegister.setOnClickListener {

            val intent = Intent(baseContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}


