package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizgame.databinding.ActivityResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResultActivity : AppCompatActivity() {

    private lateinit var resultBinding : ActivityResultBinding

    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.reference.child("scores")

    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser

    private var userCorrect = ""
    private var userWrong = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultBinding = ActivityResultBinding.inflate(layoutInflater)
        val view = resultBinding.root
        setContentView(view)

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               user?.let{
                   val userUID = it.uid

                   userCorrect = snapshot.child(userUID).child("correct").toString()
                   userWrong = snapshot.child(userUID).child("wrong").toString()

                   resultBinding.textViewScoreCorrect.text = userCorrect
                   resultBinding.textViewScoreWrong.text = userWrong
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        resultBinding.buttonPlayAgain.setOnClickListener {
            val intent = Intent(this@ResultActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        resultBinding.buttonExit.setOnClickListener {
            finish()
        }

    }
}