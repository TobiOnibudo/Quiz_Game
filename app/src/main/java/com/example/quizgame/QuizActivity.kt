package com.example.quizgame

import android.graphics.Color
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

    private var userAnswer = ""

    private var userCorrect = 0
    private var userWrong = 0

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
            userAnswer = "a"
            if (correctAnswer == userAnswer )
            {
                quizBinding.textViewA.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.textViewCorrect.text = userCorrect.toString()
            }
            else
            {
                quizBinding.textViewA.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.textViewCorrect.text = userWrong.toString()
                findAnswer()
            }
            disableClickableOfOptions()
        }

        quizBinding.textViewB.setOnClickListener {
            userAnswer = "b"
            if (correctAnswer == userAnswer )
            {
                quizBinding.textViewB.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.textViewCorrect.text = userCorrect.toString()
            }
            else
            {
                quizBinding.textViewB.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.textViewCorrect.text = userWrong.toString()
                findAnswer()
            }
            disableClickableOfOptions()
        }
        quizBinding.textViewC.setOnClickListener {
            userAnswer = "c"
            if (correctAnswer == userAnswer )
            {
                quizBinding.textViewC.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.textViewCorrect.text = userCorrect.toString()
            }
            else
            {
                quizBinding.textViewC.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.textViewCorrect.text = userWrong.toString()
                findAnswer()
            }
            disableClickableOfOptions()
        }

        quizBinding.textViewD.setOnClickListener {
            userAnswer = "d"
            if (correctAnswer == userAnswer )
            {
                quizBinding.textViewD.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.textViewCorrect.text = userCorrect.toString()
            }
            else
            {
                quizBinding.textViewD.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.textViewCorrect.text = userWrong.toString()
                findAnswer()
            }
            disableClickableOfOptions()
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


    fun findAnswer()
    {
        when (correctAnswer)
        {
            "a" -> quizBinding.textViewA.setBackgroundColor(Color.GREEN)
            "b" -> quizBinding.textViewB.setBackgroundColor(Color.GREEN)
            "c" -> quizBinding.textViewC.setBackgroundColor(Color.GREEN)
            "d" -> quizBinding.textViewD.setBackgroundColor(Color.GREEN)
        }

    }


    fun disableClickableOfOptions()
    {
        quizBinding.textViewA.isClickable = false
        quizBinding.textViewB.isClickable = false
        quizBinding.textViewC.isClickable = false
        quizBinding.textViewD.isClickable = false

    }

}