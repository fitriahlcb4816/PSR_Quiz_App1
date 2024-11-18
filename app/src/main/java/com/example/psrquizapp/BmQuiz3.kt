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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BmQuiz3 : AppCompatActivity() {

    private lateinit var questionTextView3: TextView
    private lateinit var optionsRadioGroup3: RadioGroup
    private lateinit var option1RadioButton3: RadioButton
    private lateinit var option2RadioButton3: RadioButton
    private lateinit var option3RadioButton3: RadioButton
    private lateinit var option4RadioButton3: RadioButton
    private lateinit var submitButton3: Button
    private lateinit var scoreTextView3: TextView
    private lateinit var timerTextView3: TextView
    private lateinit var progressTextView3: TextView

    private lateinit var quiz: QuizQuestion
    private lateinit var timer: CountDownTimer
    private var timeLeftInMillis: Long = 180000 // Timer in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        setContentView(R.layout.activity_bm_quiz3)
        questionTextView3 = findViewById(R.id.questionTextView3)
        optionsRadioGroup3 = findViewById(R.id.optionsRadioGroup3)
        option1RadioButton3 = findViewById(R.id.option1RadioButton3)
        option2RadioButton3 = findViewById(R.id.option2RadioButton3)
        option3RadioButton3 = findViewById(R.id.option3RadioButton3)
        option4RadioButton3 = findViewById(R.id.option4RadioButton3)
        submitButton3 = findViewById(R.id.submitButton3)
        scoreTextView3 = findViewById(R.id.scoreTextView3)
        timerTextView3 = findViewById(R.id.timerTextView3)
        progressTextView3 = findViewById(R.id.progressTextView3)

        // Initialize a list of questions
        val questions = listOf(
            Question("Apakah ERTI PERIBAHASA Potong haluan", listOf("Mengambil Peluang orang lain", "perbuatan yang sia-sia", "Sukar untuk membuat pilihan", "Menyambut sesuatu dengan senang hati"), 0),
            Question("Apakah ERTI PERIBAHASA untuk Otak udang?", listOf("Curiga", "Bijak", "Hidup", "Bodoh"), 3),
            Question("Pilih satu jawaban untuk ERTI PERIBAHASA ini - Takut kerana salah, berani kerana benar", listOf("Seseorang akan berasa takut kerana kesalahannya, sebaliknya dia berani jika dia benar", "Suka duka sama ditanggung", "Buang yang jahat ambil yang baik", "Saling membantu"), 0),
            Question("Apakah ERTI PERIBAHASA Nyawa - Nyawa ikan", listOf("Mengalami kekecewaan", "Hampir-hampir mati", "Perasaan Cemburu", "Peramah"), 1),
            Question("Apakah ERTI PERIBAHASA untuk Cakar Ayam?", listOf("Suka mencuri", "Cuba memperdaya", "Tulisan  yang buruk", "Hilang keberanian"), 2),
            Question("Pilih satu jawaban untuk ERTI PERIBAHASA ini - Bagai aur dengan tebing", listOf("Bunyi jatuh yang kuat", "Perasaan yang sangat pedih", "Tolong-menolong antara satu dengan yang lain", "Kasih sayang yang lekas hilang"), 2),
            Question("Pilih satu jawaban untuk ERTI PERIBAHASA ini - Ingat sebelum kena, jimat sebelum habis", listOf("Selalu berwaaspada dan berhati - hati", "janji mesti ditepati", "Ambillah yang baik, buanglah yang jahat", "Anak menurut baka ibu bapanya"), 0),
            Question("Apakah ERTI PERIBAHASA Lidah bercabang", listOf("Kata-kata yang tidak dapat dipercayai", "befikir atau belajar dengan kuat", "Sudah pasti akan diperolehi", "Menggelikan hati"), 0),
            Question("Apakah ERTI PERIBAHASA untuk Tin Kosong?", listOf("Tidak tahu malu", "Tidak bersemangat", "Becakap besar tanpa ilmu", "Sangat marah atau sangat benci"), 2),
            Question("Pilih satu jawaban untuk ERTI PERIBAHASA ini - Diam - diam ubi berisi, diam besi berkarat", listOf("Janji mesti ditepati", "Pendiam tetapi befikir atau banyak pengetahuan", "Sabar mengerjakan sesuatu, lama-lama berhasil juga", "Orang yang dapat menyesuaikan diri dalam pergaulan"), 1)
        )

        // Initialize the Quiz with the questions
        quiz = QuizQuestion(questions)
        loadNextQuestion()

        // Start the timer
        startTimer()

        // Set the listener for the submit button
        submitButton3.setOnClickListener {
            handleAnswerSubmission()
        }
    }

    private fun handleAnswerSubmission() {
        val selectedOptionId = optionsRadioGroup3.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOptionIndex = when (selectedOptionId) {
                R.id.option1RadioButton3 -> 0
                R.id.option2RadioButton3 -> 1
                R.id.option3RadioButton3 -> 2
                R.id.option4RadioButton3 -> 3
                else -> -1
            }

            if (selectedOptionIndex != -1) {
                val isCorrect = quiz.checkAnswer(selectedOptionIndex)

                // Show Toast for feedback
                if (isCorrect) {
                    Toast.makeText(this, "Betul!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Salah!", Toast.LENGTH_SHORT).show()
                }

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
        questionTextView3.text = currentQuestion.questionText
        option1RadioButton3.text = currentQuestion.options[0]
        option2RadioButton3.text = currentQuestion.options[1]
        option3RadioButton3.text = currentQuestion.options[2]
        option4RadioButton3.text = currentQuestion.options[3]
        optionsRadioGroup3.clearCheck() // Clear any previous selections

        // Update the progress text
        val currentQuestionIndex = quiz.getCurrentQuestionIndex() + 1 // +1 to make it 1-based
        val totalQuestions = quiz.getTotalQuestions()
        progressTextView3.text = "Question $currentQuestionIndex of $totalQuestions"
        updateScore()
    }

    private fun updateScore() {
        scoreTextView3.text = "Score: ${quiz.getScore()}"
    }

    private fun displayFinalScore() {
        // Get the final score from QuizQuestion class and update the UI
        scoreTextView3.text = "Final Score: ${quiz.getScore()}"
        scoreTextView3.visibility = View.VISIBLE
        submitButton3.isEnabled = false
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
            else -> "Sila diperbaiki"
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
        timerTextView3.text = String.format("%02d:%02d", minutes, secondsRemaining)
    }
}