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

class MQuiz2 : AppCompatActivity() {

    private lateinit var questionTextViewM1: TextView
    private lateinit var optionsRadioGroupM1: RadioGroup
    private lateinit var option1RadioButtonM1: RadioButton
    private lateinit var option2RadioButtonM1: RadioButton
    private lateinit var option3RadioButtonM1: RadioButton
    private lateinit var option4RadioButtonM1: RadioButton
    private lateinit var submitButtonM1: Button
    private lateinit var scoreTextViewM1: TextView
    private lateinit var timerTextViewM1: TextView
    private lateinit var progressTextViewM1: TextView
    private lateinit var questionImageViewM1: ImageView

    private lateinit var quiz: QuizQuestion
    private lateinit var timer: CountDownTimer
    private var timeLeftInMillis: Long = 1800000 // Timer in milliseconds


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mquiz2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        questionTextViewM1 = findViewById(R.id.questionTextViewM1)
        optionsRadioGroupM1 = findViewById(R.id.optionsRadioGroupM1)
        option1RadioButtonM1 = findViewById(R.id.option1RadioButtonM1)
        option2RadioButtonM1 = findViewById(R.id.option2RadioButtonM1)
        option3RadioButtonM1 = findViewById(R.id.option3RadioButtonM1)
        option4RadioButtonM1 = findViewById(R.id.option4RadioButtonM1)
        submitButtonM1 = findViewById(R.id.submitButtonM1)
        scoreTextViewM1 = findViewById(R.id.scoreTextViewM1)
        timerTextViewM1 = findViewById(R.id.timerTextViewM1)
        progressTextViewM1 = findViewById(R.id.progressTextViewM1)
        questionImageViewM1 = findViewById(R.id.questionImageViewM1)

        // Initialize a list of questions
        val questions = listOf(
            Question("Calculate the new price of the mobile phone", listOf("$240.00", "$260.00", "$200.00", "$220.00"), 0, R.drawable.soalan1),
            Question("What is the new price after discount?", listOf("$20.00", "$29.00", "$18.00", "$12.00"), 2, R.drawable.soalan2),
            Question("$100 is to be divided among Ayu, Peet and Adeek. Ayu gets 25% of it and Peet gets 30%. How much value of money does Adeek get?", listOf("$50.00", "$40.00", "$55.00", "$45.00"), 3),
            Question("In a test, Irah scored 76% of a possible marks of 300. How many marks did Abdul Score?", listOf("250", "228", "230", "299"), 1),
            Question("The price of 10kg durians wa $69.00. It was then reduced by 20%. What was the new price?", listOf("$50.00", "$55.20", "$55.00", "$50.20"), 1),
            Question("Wafiq earns $1800 per month. He spends $1530 on household needs. What percent does he save?", listOf("27%", "25%", "15%", "10%"), 2),
            Question("35% of 340 students were absent from the test. How many students were absent from the test?", listOf("119 Students", "100 Students", "150 Students", "99 Students"), 0),
            Question("During the Brunei Grand sale, a baju kurung which costs $45.00 was given a discount of 10%. How much is the new price?", listOf("$50.00", "$40.00", "$40.50", "$35.50"), 2),
            Question("Forty pupils sat for a mathematics test. 6 pupils were failed. What percentage of the pupils passed the test?", listOf("90%", "75%", "95%", "85%"), 3),
            Question("A school library has a collection of 400 books of which 80 are new. Find the percentage of the new books", listOf("20%", "40%", "75%", "80%"), 0)
        )

        // Initialize the Quiz with the questions
        quiz = QuizQuestion(questions)
        loadNextQuestion()

        // Start the timer
        startTimer()

        // Set the listener for the submit button
        submitButtonM1.setOnClickListener {
            handleAnswerSubmission()
        }

    }

    private fun handleAnswerSubmission() {
        val selectedOptionId = optionsRadioGroupM1.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOptionIndex = when (selectedOptionId) {
                R.id.option1RadioButtonM1 -> 0
                R.id.option2RadioButtonM1 -> 1
                R.id.option3RadioButtonM1 -> 2
                R.id.option4RadioButtonM1 -> 3
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
        questionTextViewM1.text = currentQuestion.questionText
        option1RadioButtonM1.text = currentQuestion.options[0]
        option2RadioButtonM1.text = currentQuestion.options[1]
        option3RadioButtonM1.text = currentQuestion.options[2]
        option4RadioButtonM1.text = currentQuestion.options[3]
        optionsRadioGroupM1.clearCheck() // Clear any previous selections

        // Update the progress text
        val currentQuestionIndex = quiz.getCurrentQuestionIndex() + 1 // +1 to make it 1-based
        val totalQuestions = quiz.getTotalQuestions()
        progressTextViewM1.text = "Question $currentQuestionIndex of $totalQuestions"
        updateScore()

        // Set the question image if there is one
        currentQuestion.imageResId?.let { imageResId ->
            questionImageViewM1.setImageResource(imageResId)
            questionImageViewM1.visibility = View.VISIBLE
        } ?: run {
            questionImageViewM1.visibility = View.GONE // Hide if no image
        }

        // Set up options and progress text (as before)
    }

    private fun updateScore() {
        scoreTextViewM1.text = "Score: ${quiz.getScore()}"
    }

    private fun displayFinalScore() {
        // Get the final score from QuizQuestion class and update the UI
        scoreTextViewM1.text = "Final Score: ${quiz.getScore()}"
        scoreTextViewM1.visibility = View.VISIBLE
        submitButtonM1.isEnabled = false
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
        timerTextViewM1.text = String.format("%02d:%02d", minutes, secondsRemaining)
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
