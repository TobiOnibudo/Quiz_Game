package com.example.quizgame

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quizgame.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

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
    private var questionNumber = 0

    private var userAnswer = ""

    private var userCorrect = 0
    private var userWrong = 0

    private lateinit var timer: CountDownTimer
    private val totalTime = 25000L
    private var timerContinue = false
    private var leftTime = totalTime

    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser
    private val scoreRef = database.reference


    private val questions = HashSet<Int>()
    private val numberOfQuestions = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding = ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        setContentView(view)

        do{
           val number =  Random.nextInt(1,6)
            questions.add(number)
        }while (questions.size < numberOfQuestions)

        Log.d("numbersOfQuestions",questions.toString())
        gameLogic()

        quizBinding.buttonNext.setOnClickListener {
            resetTimer()
            gameLogic()
        }

        quizBinding.buttonFinish.setOnClickListener {
            sendScore()
        }

        quizBinding.textViewA.setOnClickListener {
            pauseTimer()

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
                quizBinding.textViewWrong.text = userWrong.toString()
                findAnswer()
            }
            disableClickableOfOptions()
        }

        quizBinding.textViewB.setOnClickListener {
            pauseTimer()

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
                quizBinding.textViewWrong.text = userWrong.toString()
                findAnswer()
            }
            disableClickableOfOptions()
        }
        quizBinding.textViewC.setOnClickListener {
            pauseTimer()

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
                quizBinding.textViewWrong.text = userWrong.toString()
                findAnswer()
            }
            disableClickableOfOptions()
        }

        quizBinding.textViewD.setOnClickListener {
            pauseTimer()

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
                quizBinding.textViewWrong.text = userWrong.toString()
                findAnswer()
            }
            disableClickableOfOptions()
        }
    }


    private fun gameLogic()
    {
        restoreOptions()

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                questionCount = snapshot.childrenCount

                if (questionNumber < questions.size) {
                    question = snapshot.child(questions.elementAt(questionNumber).toString()).child("q").value.toString()
                    answerA = snapshot.child(questions.elementAt(questionNumber).toString()).child("a").value.toString()
                    answerB = snapshot.child(questions.elementAt(questionNumber).toString()).child("b").value.toString()
                    answerC = snapshot.child(questions.elementAt(questionNumber).toString()).child("c").value.toString()
                    answerD = snapshot.child(questions.elementAt(questionNumber).toString()).child("d").value.toString()
                    correctAnswer =
                        snapshot.child(questions.elementAt(questionNumber).toString()).child("answer").value.toString()

                    quizBinding.textViewQuestion.text = question
                    quizBinding.textViewA.text = answerA
                    quizBinding.textViewB.text = answerB
                    quizBinding.textViewC.text = answerC
                    quizBinding.textViewD.text = answerD

                    quizBinding.progressBarQuiz.visibility = View.INVISIBLE
                    quizBinding.linearLayoutQuestion.visibility  = View.VISIBLE
                    quizBinding.linearLayoutInfo.visibility  = View.VISIBLE
                    quizBinding.linearLayoutButtons.visibility  = View.VISIBLE

                    startTimer()
                }
                else
                {
                     val dialogMessage = AlertDialog.Builder(this@QuizActivity)
                    dialogMessage.setTitle("Quiz Game")
                    dialogMessage.setMessage("Congratulations!!!\nYou have answered all the questions. Do you want to see the result?")
                    dialogMessage.setCancelable(false)
                    dialogMessage.setPositiveButton("See Result"){ dialogWindow,position ->
                        sendScore()
                    }

                    dialogMessage.setNegativeButton("Play Again"){dialogWindow,position ->
                        val intent = Intent(this@QuizActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    dialogMessage.create().show()
                }

                questionNumber++

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun findAnswer()
    {
        when (correctAnswer)
        {
            "a" -> quizBinding.textViewA.setBackgroundColor(Color.GREEN)
            "b" -> quizBinding.textViewB.setBackgroundColor(Color.GREEN)
            "c" -> quizBinding.textViewC.setBackgroundColor(Color.GREEN)
            "d" -> quizBinding.textViewD.setBackgroundColor(Color.GREEN)
        }

    }


    private fun disableClickableOfOptions()
    {
        quizBinding.textViewA.isClickable = false
        quizBinding.textViewB.isClickable = false
        quizBinding.textViewC.isClickable = false
        quizBinding.textViewD.isClickable = false

    }

    private fun restoreOptions()
    {
        quizBinding.textViewA.setBackgroundColor(Color.WHITE)
        quizBinding.textViewB.setBackgroundColor(Color.WHITE)
        quizBinding.textViewC.setBackgroundColor(Color.WHITE)
        quizBinding.textViewD.setBackgroundColor(Color.WHITE)


        quizBinding.textViewA.isClickable = true
        quizBinding.textViewB.isClickable = true
        quizBinding.textViewC.isClickable = true
        quizBinding.textViewD.isClickable = true

    }

    private fun startTimer()
    {
        timer = object  : CountDownTimer(leftTime,1000)
        {
            override fun onTick(millisUntilFinish: Long) {
                leftTime = millisUntilFinish
                updateCountDownText()
            }

            override fun onFinish() {
                resetTimer()
                updateCountDownText()
                quizBinding.textViewQuestion.text = "Sorry, Time is up for this question!"
                timerContinue = false
                disableClickableOfOptions()

            }

        }.start()

        timerContinue = true
    }



    private fun updateCountDownText() {
        val remainingTime : Int = (leftTime/1000).toInt()
        quizBinding.textViewTime.text = remainingTime.toString()
    }

    private fun pauseTimer()
    {
        timer.cancel()
        timerContinue = false
    }

    private fun resetTimer() {
        pauseTimer()
        leftTime = totalTime
        updateCountDownText()
    }



    private fun sendScore()
    {
        // players scores to be stored in database after finishing the game
        user?.let {
            val userUID = it.uid
            scoreRef.child("scores").child(userUID).child("correct").setValue(userCorrect)
            scoreRef.child("scores").child(userUID).child("wrong").setValue(userWrong).addOnSuccessListener {
                Toast.makeText(applicationContext,"Scores sent to database successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@QuizActivity,ResultActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }


}