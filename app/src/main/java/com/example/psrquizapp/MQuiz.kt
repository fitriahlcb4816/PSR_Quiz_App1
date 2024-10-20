package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MQuiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mquiz)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val QuizM1 = findViewById<Button>(R.id.QuizM1)
        QuizM1.setOnClickListener{
            val intent = Intent (this, MQuiz1::class.java)
            startActivity(intent)
        }
    }
}