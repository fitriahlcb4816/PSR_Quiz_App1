package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class BMQuiz4 : AppCompatActivity() {

    private lateinit var questionTextView1: TextView
    private lateinit var optionsRadioGroup1: RadioGroup
    private lateinit var option1RadioButton1: RadioButton
    private lateinit var option2RadioButton1: RadioButton
    private lateinit var option3RadioButton1: RadioButton
    private lateinit var option4RadioButton1: RadioButton
    private lateinit var submitButton1: Button
    private lateinit var scoreTextView1: TextView
    private lateinit var timerTextView1: TextView
    private lateinit var progressTextView1: TextView

    private lateinit var quiz: QuizQuestion
    private lateinit var timer: CountDownTimer
    private var timeLeftInMillis: Long = 180000 // Timer in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmquiz4)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        questionTextView1 = findViewById(R.id.questionTextView1)
        optionsRadioGroup1 = findViewById(R.id.optionsRadioGroup1)
        option1RadioButton1 = findViewById(R.id.option1RadioButton1)
        option2RadioButton1 = findViewById(R.id.option2RadioButton1)
        option3RadioButton1 = findViewById(R.id.option3RadioButton1)
        option4RadioButton1 = findViewById(R.id.option4RadioButton1)
        submitButton1 = findViewById(R.id.submitButton1)
        scoreTextView1 = findViewById(R.id.scoreTextView1)
        timerTextView1 = findViewById(R.id.timerTextView1)
        progressTextView1 = findViewById(R.id.progressTextView1)

        // Initialize a list of questions
        val questions = listOf(
            Question("Nazirah masih tidak membuat kerja rumah ................. guru-gurunya sudah berkali-kali menasihatinya", listOf("Padahal", "Sementara", "Agar", "Sejurus"), 0),
            Question("Hamizah mencuci pakaian ................. adiknya menyapu lantai", listOf("Sejurus", "Padahal", "Sementara", "Apabila"), 2),
            Question("Pertahanan negara perlu diperkuat ................. keamanan negera sentiasa terjamin", listOf("Apabila", "Sejurus", "Sementara", "Agar"), 3),
            Question("Nadzril terkejut ................. mendengar kejadian tersebut", listOf("Apabila", "Padahal", "Agar", "Sejurus"), 0),
            Question("................. sebelum kejadian itu berlaku, mereka kelihatan bermain bola di tepi pantai", listOf("Sementara", "Padahal", "Sejurus", "Apabila"), 2),
            Question("Pemanah itu melepaskan anak panah, ................. tidak mengena sasaran kerana busurnya patah", listOf("Sejak", "Sehingga", "Sementara", "Tetapi"), 3),
            Question("Berusahalah sekuat mungkin ................. badan masih sihat", listOf("Sehingga", "Sebelum", "Sejak", "Sementara"), 3),
            Question("Apik berkawan dengannya ................. kecil lagi", listOf("Sejak", "Sebelum", "Tetapi", "Sehingga"), 0),
            Question("Ikan keli perlu dibasuh dengan air asam jawa ................. dimasak agar tidak hanyir ", listOf("Sementara", "Sejak", "Sebelum", "Sehingga"), 2),
            Question("Ibu menggaul semua bahan-bahan itu menggunakan mesin  ................. kembang", listOf("Sejak", "Sebelum", "Sementara", "Sehingga"), 3)
        )

        // Initialize the Quiz with the questions
        quiz = QuizQuestion(questions)
        loadNextQuestion()

        // Start the timer
        startTimer()

        // Set the listener for the submit button
        submitButton1.setOnClickListener {
            handleAnswerSubmission()
        }
    }

    private fun handleAnswerSubmission() {
        val selectedOptionId = optionsRadioGroup1.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOptionIndex = when (selectedOptionId) {
                R.id.option1RadioButton1 -> 0
                R.id.option2RadioButton1 -> 1
                R.id.option3RadioButton1 -> 2
                R.id.option4RadioButton1 -> 3
                else -> -1
            }

            if (selectedOptionIndex != -1) {
                val isCorrect = quiz.checkAnswer(selectedOptionIndex)

                // Show Toast for feedback and Log for debugging
                if (isCorrect) {
                    Toast.makeText(this, "Betul!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Salah!", Toast.LENGTH_SHORT).show()
                }
                Log.d("Quiz", "Selected Option Index: $selectedOptionIndex, Correct: $isCorrect")

                // Update score in the UI immediately
                updateScore()

                if (quiz.hasMoreQuestions()) {
                    loadNextQuestion()
                } else {
                    displayFinalScore()
                }
            }
        } else {
            Toast.makeText(this, "Sila buat pilihan", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadNextQuestion() {
        // Get the current question and display it
        val currentQuestion = quiz.getCurrentQuestion()
        questionTextView1.text = currentQuestion.questionText
        option1RadioButton1.text = currentQuestion.options[0]
        option2RadioButton1.text = currentQuestion.options[1]
        option3RadioButton1.text = currentQuestion.options[2]
        option4RadioButton1.text = currentQuestion.options[3]
        optionsRadioGroup1.clearCheck() // Clear any previous selections

        // Update the progress text
        val currentQuestionIndex = quiz.getCurrentQuestionIndex() + 1 // +1 to make it 1-based
        val totalQuestions = quiz.getTotalQuestions()
        progressTextView1.text = "Question $currentQuestionIndex of $totalQuestions"
        updateScore()
    }

    private fun updateScore() {
        scoreTextView1.text = "Score: ${quiz.getScore()}"
    }


    private fun displayFinalScore() {
        // Get the final score from QuizQuestion class and update the UI
        scoreTextView1.text = "Final Score: ${quiz.getScore()}"
        scoreTextView1.visibility = View.VISIBLE
        submitButton1.isEnabled = false
        timer.cancel() // Stop the timer when the quiz ends

        // Navigate to the performance result page
        val finalScore = quiz.getScore()
        val totalQuestions = quiz.getTotalQuestions()
        val performance = getPerformance(finalScore, totalQuestions)

        // Pass data to PerformanceActivity
        val intent = Intent(this, PerformanceActivity::class.java).apply {
            putExtra("SCORE", finalScore)
            putExtra("TOTAL_QUESTIONS", totalQuestions)
            putExtra("PERFORMANCE", performance)
        }
        startActivity(intent)
    }

    private fun getPerformance(finalScore: Int, totalQuestions: Int): String {
        val correctPercentage = (finalScore.toFloat() / totalQuestions) * 100
        return when {
            correctPercentage >= 80 -> "Cemerlang"
            correctPercentage >= 60 -> "Bagus"
            correctPercentage >= 40 -> "Sederhana"
            else -> "Sila Buat pilihan"
        }
    }

    // Timer Methods
    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                // Handle when timer finishes (e.g., end the quiz)
                displayFinalScore()
            }
        }
        timer.start()
    }

    private fun updateTimer() {
        val seconds = (timeLeftInMillis / 1000).toInt()
        val minutes = seconds / 60
        val secondsRemaining = seconds % 60
        timerTextView1.text = String.format("%02d:%02d", minutes, secondsRemaining)
    }

}
