package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quizgame.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        loginBinding.buttonSignin.setOnClickListener {
            val userEmail  = loginBinding.editTextLoginEmail.text.toString()
            val userPassword  = loginBinding.editTextLoginPassword.text.toString()

            loginBinding.progressBarLogin.visibility = View.VISIBLE
            signInUser(userEmail,userPassword)
        }

        loginBinding.buttonGoogleSignin.setOnClickListener {

        }

        loginBinding.textViewSignup.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

        loginBinding.textViewForgotPassword.setOnClickListener {
            val intent = Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signInUser(userEmail: String, userPassword: String) {

        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener {task->
            if(task.isSuccessful)
            {
                loginBinding.progressBarLogin.visibility = View.INVISIBLE
                Toast.makeText(applicationContext,"Welcome to Quiz Game",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser

        if (user != null)
        {
            loginBinding.progressBarLogin.visibility = View.INVISIBLE
            Toast.makeText(applicationContext,"Welcome to Quiz Game",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}