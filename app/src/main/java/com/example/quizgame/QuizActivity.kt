package com.example.quizgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizgame.databinding.ActivityQuizBinding
import com.google.firebase.database.FirebaseDatabase

class QuizActivity : AppCompatActivity() {

    private lateinit var quizBinding: ActivityQuizBinding

    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.reference.child("questions")

    private var question = ""
    private var answerA = ""
    private var answerB = ""
    private var answerC = ""
    private var answerD = ""
    private var correctAnswer = ""

    private var questionCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding = ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        setContentView(view)

        quizBinding.buttonNext.setOnClickListener {

        }

        quizBinding.buttonFinish.setOnClickListener {

        }

        quizBinding.textViewA.setOnClickListener {

        }

        quizBinding.textViewB.setOnClickListener {

        }
        quizBinding.textViewC.setOnClickListener {

        }

        quizBinding.textViewD.setOnClickListener {

        }

    }
}