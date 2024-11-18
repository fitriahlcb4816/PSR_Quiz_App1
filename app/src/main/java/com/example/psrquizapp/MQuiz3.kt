package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
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
    private lateinit var questionImageViewM2: ImageView

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
        questionImageViewM2 = findViewById(R.id.questionImageViewM2)

        // Initialize a list of questions
        val questions = listOf(
            Question("Amir, Borhan and Hakim have 90 marbles. Amir has 13 marbles whereas Borhan has three times as many marbles as amir. How many marbles does Hakim have?", listOf("38 Marbles", "39 Marbles", "50 Marbles", "65 marbles"), 0),
            Question("Hariz went to supermarket to buy some mangoes. The mangeoes are sold in packs of 4. He needs to buy 30 mangoes. How many packs of mangoes does he need to buy?", listOf("7.5 Packs", "7 Packs", "8 Packs", "9 Packs"), 2),
            Question("How much did Adam have to pay?", listOf("$5.00", "$3.50", "$4.00", "$4.50"), 3, R.drawable.soalan3),
            Question("Plastic cupsare sold in packs of 8. Ahmad needs 27 cups. Estimates how many packs must he buy?", listOf("3 Packs", "4 Packs", "3.5 Packs", "5 Packs"), 1),
            Question("Subtract 7012 from 9422", listOf("4201", "2410", "2140", "4297"), 1),
            Question("Add 7 to 16 and multiple the result by 3", listOf("690", "50", "69", "96"), 2),
            Question("Junaidi lost 27 stone so he has 22 stone left. How many stone did he have at first?", listOf("24", "49", "39", "30"), 1),
            Question("Aminah has $20.50. Fatimah has $2.50 lee than Aminah. How much money do they have altogether?", listOf("$38.50", "$40.50", "$55.00", "$45.50"), 0),
            Question("At an expo, an article is sold for $200. The place is reduced by $25.75. What is the new price?", listOf("$180.50", "$154.25", "$170.60", "$174.25"), 3),
            Question("Every sunday Alus reads from 11:40am to 1:55pm. How much time does she spend on reading?", listOf("2 Hours 15 Minutes", "3 Hours", "2 Hours 45 Minutes", "3 Hours 15 Minutes"), 0)
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

        // Set the question image if there is one
        currentQuestion.imageResId?.let { imageResId ->
            questionImageViewM2.setImageResource(imageResId)
            questionImageViewM2.visibility = View.VISIBLE
        } ?: run {
            questionImageViewM2.visibility = View.GONE // Hide if no image
        }

        // Set up options and progress text (as before)
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
