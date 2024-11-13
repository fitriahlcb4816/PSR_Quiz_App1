package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PerformanceActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_performance2)

        resultTextView = findViewById(R.id.resultTextView)

        // Get data passed from BMQuiz2 activity
        val score = intent.getIntExtra("SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)
        val performance = intent.getStringExtra("PERFORMANCE") ?: "No performance data"

        // Display the result
        resultTextView.text = "Score: $score/$totalQuestions\nPerformance: $performance"

        val backQuiz  = findViewById<Button>(R.id.backQuiz)
        backQuiz.setOnClickListener{
            val intent = Intent ( this, BMQuiz::class.java)
            startActivity(intent)
        }
    }

}
