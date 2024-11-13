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
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BMQuiz1 : AppCompatActivity() {

    private lateinit var questionTextView4: TextView
    private lateinit var optionsRadioGroup4: RadioGroup
    private lateinit var option1RadioButton4: RadioButton
    private lateinit var option2RadioButton4: RadioButton
    private lateinit var option3RadioButton4: RadioButton
    private lateinit var option4RadioButton4: RadioButton
    private lateinit var submitButton4: Button
    private lateinit var scoreTextView4: TextView
    private lateinit var timerTextView4: TextView
    private lateinit var progressTextView4: TextView

    private lateinit var quiz: QuizQuestion
    private lateinit var timer: CountDownTimer
    private var timeLeftInMillis: Long = 180000 // Timer in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmquiz1)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        questionTextView4 = findViewById(R.id.questionTextView4)
        optionsRadioGroup4 = findViewById(R.id.optionsRadioGroup4)
        option1RadioButton4 = findViewById(R.id.option1RadioButton4)
        option2RadioButton4 = findViewById(R.id.option2RadioButton4)
        option3RadioButton4 = findViewById(R.id.option3RadioButton4)
        option4RadioButton4 = findViewById(R.id.option4RadioButton4)
        submitButton4 = findViewById(R.id.submitButton4)
        scoreTextView4 = findViewById(R.id.scoreTextView4)
        timerTextView4 = findViewById(R.id.timerTextView4)
        progressTextView4 = findViewById(R.id.progressTextView4)

        // Initialize a list of questions
        val questions = listOf(
            Question("Apakah kata berlawanan bagi jatuh?", listOf("Bangun", "Sedar", "Curang", "Gagal"), 0),
            Question("Sila pilih jawapan yang betul, Kata berlawanan bagi rapat adalah .............. ", listOf("Sambung", "Riang", "Renggang", "Ringkas"), 2),
            Question("Apakah kata seerti bagi kecewa?", listOf("Suka", "Ancaman", "Perawan", "Gagal"), 3),
            Question("Kata seerti bagi usang adalah ..............", listOf("Sejarah", "Lama", "Berita", "Sampai"), 1),
            Question("Jawab dengan betul, apakah kata antonim bagi halus?", listOf("Busuk", "Kasar", "Hangat", "Tenteram"), 1),
            Question("Kata antonim bagi teliti adalah .............. ", listOf("Yakin", "Timbul", "Cuai", "Benar"), 2),
            Question("Sila pilih jawapan yang betul, sinonim bagi tangkis adalah ..............", listOf("Sakit", "Elak", "Senyap", "Tamat"), 1),
            Question("Kata berlawanan bagi gelap adalah ..............", listOf("Bangun", "Lama", "Terang", "Jahil"), 2),
            Question("Apakah kata sinonim bagi bahaya?", listOf("Pelik", "Kawasan", "Periksa", "Tekad"), 3),
            Question("Apakah kata seerti bagi amal?", listOf("Kebaikkan", "Peranan", "Bohong", "Singkat"), 0)
        )

        // Initialize the Quiz with the questions
        quiz = QuizQuestion(questions)
        loadNextQuestion()

        // Start the timer
        startTimer()

        // Set the listener for the submit button
        submitButton4.setOnClickListener {
            handleAnswerSubmission()
        }
    }

    private fun handleAnswerSubmission() {
        val selectedOptionId = optionsRadioGroup4.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOptionIndex = when (selectedOptionId) {
                R.id.option1RadioButton4 -> 0
                R.id.option2RadioButton4 -> 1
                R.id.option3RadioButton4 -> 2
                R.id.option4RadioButton4 -> 3
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
        questionTextView4.text = currentQuestion.questionText
        option1RadioButton4.text = currentQuestion.options[0]
        option2RadioButton4.text = currentQuestion.options[1]
        option3RadioButton4.text = currentQuestion.options[2]
        option4RadioButton4.text = currentQuestion.options[3]
        optionsRadioGroup4.clearCheck() // Clear any previous selections

        // Update the progress text
        val currentQuestionIndex = quiz.getCurrentQuestionIndex() + 1 // +1 to make it 1-based
        val totalQuestions = quiz.getTotalQuestions()
        progressTextView4.text = "Question $currentQuestionIndex of $totalQuestions"
        updateScore()
    }

    private fun updateScore() {
        scoreTextView4.text = "Score: ${quiz.getScore()}"
    }

    private fun displayFinalScore() {
        // Get the final score from QuizQuestion class and update the UI
        scoreTextView4.text = "Final Score: ${quiz.getScore()}"
        scoreTextView4.visibility = View.VISIBLE
        submitButton4.isEnabled = false
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
        timerTextView4.text = String.format("%02d:%02d", minutes, secondsRemaining)
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