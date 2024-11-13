package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Quiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_quiz)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val buttonBM = findViewById<Button>(R.id.buttonBM)
        buttonBM.setOnClickListener{
            val intent = Intent (this, BMQuiz::class.java)
            startActivity(intent)
        }

        val buttonM = findViewById<Button>(R.id.buttonM)
        buttonM.setOnClickListener{
            val intent = Intent (this, MQuiz::class.java)
            startActivity(intent)
        }

    }


}