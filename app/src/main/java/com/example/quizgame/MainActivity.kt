package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizgame.databinding.ActivityMainBinding
import com.example.quizgame.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root

        setContentView(view)

        mainBinding.buttonSignOut.setOnClickListener {
            //sign user out and move back to log in page
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}