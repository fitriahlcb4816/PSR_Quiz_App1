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

class MQuiz1 : AppCompatActivity() {

    private lateinit var questionTextViewM: TextView
    private lateinit var optionsRadioGroupM: RadioGroup
    private lateinit var option1RadioButtonM: RadioButton
    private lateinit var option2RadioButtonM: RadioButton
    private lateinit var option3RadioButtonM: RadioButton
    private lateinit var option4RadioButtonM: RadioButton
    private lateinit var submitButtonM: Button
    private lateinit var scoreTextViewM: TextView
    private lateinit var timerTextViewM: TextView
    private lateinit var progressTextViewM: TextView

    private lateinit var quiz: QuizQuestion
    private lateinit var timer: CountDownTimer
    private var timeLeftInMillis: Long = 1800000 // Timer in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mquiz1)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        questionTextViewM = findViewById(R.id.questionTextViewM)
        optionsRadioGroupM = findViewById(R.id.optionsRadioGroupM)
        option1RadioButtonM = findViewById(R.id.option1RadioButtonM)
        option2RadioButtonM = findViewById(R.id.option2RadioButtonM)
        option3RadioButtonM = findViewById(R.id.option3RadioButtonM)
        option4RadioButtonM = findViewById(R.id.option4RadioButtonM)
        submitButtonM = findViewById(R.id.submitButtonM)
        scoreTextViewM = findViewById(R.id.scoreTextViewM)
        timerTextViewM = findViewById(R.id.timerTextViewM)
        progressTextViewM = findViewById(R.id.progressTextViewM)

        // Initialize a list of questions
        val questions = listOf(
            Question("Evaluate 15.67 + 4.058 + 13", listOf("32.728", "32.827", "32.278", "32.782"), 0),
            Question("Find the value of (8 + 2 x 5) - 16 / 4", listOf("46", "20", "14", "36"), 2),
            Question("Express 6 months as a percentage of 2 years", listOf("20%", "35%", "30%", "25%"), 3),
            Question("Subtract 357 from 802 and the multiple the result by 8", listOf("3506", "3560", "3605", "3650"), 1),
            Question("How many odd numbers that are multiple by 3 between 10 and 30?", listOf("15,21,27", "15,19,27", "15,27,29", "15,25,27"), 0),
            Question("Find the sum of 21 , 0.704, 2.320", listOf("3.045", "5.124", "24.024", "24.240"), 2),
            Question("There are 30 paper plates in a pack. Ahamd buys 2 packs. He uses 43 plates. How many plates are left?", listOf("20 Plates", "17 Plates", "10 Plates", "23 Plates"), 1),
            Question("Which of the following numbers are composite?", listOf("49, 53, 55, 61, 65", "49, 53, 57, 61, 65", "49, 51, 55, 63, 65", "49, 55, 57, 61, 63"), 2),
            Question("Evaluate 16 + 2.40 + 9.0", listOf("27.40", "26.50", "11.56", "27.04"), 3),
            Question("Evaluate 0.523 x 100", listOf("52.3", "53.2", "23.5", "25.3"), 0)
        )

        // Initialize the Quiz with the questions
        quiz = QuizQuestion(questions)
        loadNextQuestion()

        // Start the timer
        startTimer()

        // Set the listener for the submit button
        submitButtonM.setOnClickListener {
            handleAnswerSubmission()
        }

    }

    private fun handleAnswerSubmission() {
        val selectedOptionId = optionsRadioGroupM.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOptionIndex = when (selectedOptionId) {
                R.id.option1RadioButtonM -> 0
                R.id.option2RadioButtonM -> 1
                R.id.option3RadioButtonM -> 2
                R.id.option4RadioButtonM -> 3
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
        questionTextViewM.text = currentQuestion.questionText
        option1RadioButtonM.text = currentQuestion.options[0]
        option2RadioButtonM.text = currentQuestion.options[1]
        option3RadioButtonM.text = currentQuestion.options[2]
        option4RadioButtonM.text = currentQuestion.options[3]
        optionsRadioGroupM.clearCheck() // Clear any previous selections

        // Update the progress text
        val currentQuestionIndex = quiz.getCurrentQuestionIndex() + 1 // +1 to make it 1-based
        val totalQuestions = quiz.getTotalQuestions()
        progressTextViewM.text = "Question $currentQuestionIndex of $totalQuestions"
        updateScore()
    }

    private fun updateScore() {
        scoreTextViewM.text = "Score: ${quiz.getScore()}"
    }

    private fun displayFinalScore() {
        // Get the final score from QuizQuestion class and update the UI
        scoreTextViewM.text = "Final Score: ${quiz.getScore()}"
        scoreTextViewM.visibility = View.VISIBLE
        submitButtonM.isEnabled = false
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
        timerTextViewM.text = String.format("%02d:%02d", minutes, secondsRemaining)
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