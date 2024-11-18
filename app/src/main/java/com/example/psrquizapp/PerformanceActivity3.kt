package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PerformanceActivity3 : AppCompatActivity() {


    private lateinit var resultTextView1: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_performance3)

        resultTextView1 = findViewById(R.id.resultTextView1)

        // Get data passed from BMQuiz2 activity
        val score = intent.getIntExtra("SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)
        val performance = intent.getStringExtra("PERFORMANCE") ?: "No performance data"

        // Display the result
        resultTextView1.text = "Score: $score/$totalQuestions\nPerformance: $performance"

        val quizBack  = findViewById<Button>(R.id.quizBack)
        quizBack.setOnClickListener{
            val intent = Intent ( this, MQuiz::class.java)
            startActivity(intent)
        }
    }

}