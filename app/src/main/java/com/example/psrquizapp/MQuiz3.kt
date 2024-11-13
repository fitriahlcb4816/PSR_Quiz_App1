package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MQuiz3 : AppCompatActivity() {

    private lateinit var questionTextViewM2: TextView
    private lateinit var optionsRadioGroupM2: RadioGroup
    private lateinit var option1RadioButtonM2: RadioButton
    private lateinit var option2RadioButtonM2: RadioButton
    private lateinit var option3RadioButtonM2: RadioButton
    private lateinit var option4RadioButtonM2: RadioButton
    private lateinit var submitButtonM2: Button
    private lateinit var scoreTextViewM2: TextView
    private lateinit var timerTextViewM2: TextView
    private lateinit var progressTextViewM2: TextView

    private lateinit var quiz: QuizQuestion
    private lateinit var timer: CountDownTimer
    private var timeLeftInMillis: Long = 1800000 // Timer in milliseconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mquiz3)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        questionTextViewM2 = findViewById(R.id.questionTextViewM2)
        optionsRadioGroupM2 = findViewById(R.id.optionsRadioGroupM2)
        option1RadioButtonM2 = findViewById(R.id.option1RadioButtonM2)
        option2RadioButtonM2 = findViewById(R.id.option2RadioButtonM2)
        option3RadioButtonM2 = findViewById(R.id.option3RadioButtonM2)
        option4RadioButtonM2 = findViewById(R.id.option4RadioButtonM2)
        submitButtonM2 = findViewById(R.id.submitButtonM2)
        scoreTextViewM2 = findViewById(R.id.scoreTextViewM2)
        timerTextViewM2 = findViewById(R.id.timerTextViewM2)
        progressTextViewM2 = findViewById(R.id.progressTextViewM2)

        // Initialize a list of questions
        val questions = listOf(
            Question("Buang sampah yang bertaburan itu ke dalam tong sampah, kata ayah kepada anaknya?", listOf("Ayat Suruhan", "Ayat Seruan", "Ayat Penyata", "Ayat Larangan"), 0),
            Question("Wah, besarnya Villa di atas bukit itu!", listOf("Ayat Penyata", "Ayat Tanya", "Ayat Seruan", "Ayat Suruhan"), 2),
            Question("Amanda tidak pergi ke sekolah hari ini", listOf("Ayat Seruan", "Ayat Suruhan", "Ayat Larangan", "Ayat Penyata"), 3),
            Question("Bilakah tarikh hari ulang tahun Baginda?", listOf("Ayat Suruhan", "Ayat Tanya", "Ayat Penyata", "Ayat Larangan"), 1),
            Question("Tidak usah awak campur tangan dalam hal peribadi saya", listOf("Ayat Penyata", "Ayat Larangan", "Ayat Seruan", "Ayat Suruhan"), 1),
            Question("Tolong simpan buku-buku ini di bilik guru", listOf("Ayat Tanya", "Ayat Langan", "Ayat Suruhan", "Ayat Penyata"), 2),
            Question("Bagaimana awak membuat kuih ini?", listOf("Ayat Suruhan", "Ayat Tanya", "Ayat Penyata", "Ayat Larangan"), 1),
            Question("Oh, saya lupa membawa uang hari ini!", listOf("Ayat Penyata", "Ayat Tanya", "Ayat Seruan", "Ayat Suruhan"), 2),
            Question("Dia suka menonton rancangan televisyen", listOf("Ayat Seruan", "Ayat Suruhan", "Ayat Larangan", "Ayat Penyata"), 3),
            Question("Jangan bermain mercun, nanti terbakar tangan", listOf("Ayat Larangan", "Ayat Seruan", "Ayat Tanya", "Ayat Penyata"), 0)
        )

        // Initialize the Quiz with the questions
        quiz = QuizQuestion(questions)
        loadNextQuestion()

        // Start the timer
        startTimer()

        // Set the listener for the submit button
        submitButtonM2.setOnClickListener {
            handleAnswerSubmission()
        }

    }

    private fun handleAnswerSubmission() {
        val selectedOptionId = optionsRadioGroupM2.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOptionIndex = when (selectedOptionId) {
                R.id.option1RadioButtonM2 -> 0
                R.id.option2RadioButtonM2 -> 1
                R.id.option3RadioButtonM2 -> 2
                R.id.option4RadioButtonM2 -> 3
                else -> -1
            }

            if (selectedOptionIndex != -1) {
                val isCorrect = quiz.checkAnswer(selectedOptionIndex)

                // Show Toast for feedback
                if (isCorrect) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadNextQuestion() {
        // Get the current question and display it
        val currentQuestion = quiz.getCurrentQuestion()
        questionTextViewM2.text = currentQuestion.questionText
        option1RadioButtonM2.text = currentQuestion.options[0]
        option2RadioButtonM2.text = currentQuestion.options[1]
        option3RadioButtonM2.text = currentQuestion.options[2]
        option4RadioButtonM2.text = currentQuestion.options[3]
        optionsRadioGroupM2.clearCheck() // Clear any previous selections

        // Update the progress text
        val currentQuestionIndex = quiz.getCurrentQuestionIndex() + 1 // +1 to make it 1-based
        val totalQuestions = quiz.getTotalQuestions()
        progressTextViewM2.text = "Question $currentQuestionIndex of $totalQuestions"
        updateScore()
    }

    private fun updateScore() {
        scoreTextViewM2.text = "Score: ${quiz.getScore()}"
    }

    private fun displayFinalScore() {
        // Get the final score from QuizQuestion class and update the UI
        scoreTextViewM2.text = "Final Score: ${quiz.getScore()}"
        scoreTextViewM2.visibility = View.VISIBLE
        submitButtonM2.isEnabled = false
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
            correctPercentage >= 80 -> "Excellent"
            correctPercentage >= 60 -> "Good"
            correctPercentage >= 40 -> "Average"
            else -> "Needs Improvement"
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
        timerTextViewM2.text = String.format("%02d:%02d", minutes, secondsRemaining)
    }

    private fun savePerformance(score: Int, timeTaken: Long) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        // Retrieve student's name (assume this was saved earlier in the profile collection)
        db.collection("profiles").document(userId!!).get()
            .addOnSuccessListener { document ->
                val name = document.getString("name") ?: "Unknown"
                val performance = StudentPerformance(name, score, timeTaken)

                // Save to "leaderboard" collection
                db.collection("leaderboard").add(performance)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Performance saved to leaderboard", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to save performance: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            }

    }


}
