package com.example.quizgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quizgame.databinding.ActivityQuizBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

    private var questionCount : Long = 0
    private var questionNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding = ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        setContentView(view)

        quizBinding.buttonNext.setOnClickListener {
            gameLogic()
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


    private fun gameLogic()
    {
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                questionCount = snapshot.childrenCount

                if (questionNumber <= questionCount) {
                    question = snapshot.child(questionNumber.toString()).child("q").value.toString()
                    answerA = snapshot.child(questionNumber.toString()).child("a").value.toString()
                    answerB = snapshot.child(questionNumber.toString()).child("b").value.toString()
                    answerC = snapshot.child(questionNumber.toString()).child("c").value.toString()
                    answerD = snapshot.child(questionNumber.toString()).child("d").value.toString()
                    correctAnswer =
                        snapshot.child(questionNumber.toString()).child("answer").value.toString()

                    quizBinding.textViewQuestion.text = question
                    quizBinding.textViewA.text = answerA
                    quizBinding.textViewB.text = answerB
                    quizBinding.textViewC.text = answerC
                    quizBinding.textViewD.text = answerD

                    quizBinding.progressBarQuiz.visibility = View.INVISIBLE
                    quizBinding.linearLayoutQuestion.visibility  = View.VISIBLE
                    quizBinding.linearLayoutInfo.visibility  = View.VISIBLE
                    quizBinding.linearLayoutButtons.visibility  = View.VISIBLE
                }
                else
                {
                    Toast.makeText(applicationContext,"You've answered all the questions",Toast.LENGTH_SHORT).show()
                }

                questionNumber++

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_SHORT).show()
            }

        })
    }
}