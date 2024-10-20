package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val intent = Intent (this, Material::class.java)
            startActivity(intent)
        }

        val btnQuiz = findViewById<Button>(R.id.btnQuiz)
        btnQuiz.setOnClickListener{
            val intent = Intent (this, Quiz::class.java)
            startActivity(intent)
        }

        val btnPerformance = findViewById<Button>(R.id.btnPerformance)
        btnPerformance.setOnClickListener{
            val intent = Intent (this, Performance::class.java)
            startActivity(intent)
        }

        val btnLeaderBoard = findViewById<Button>(R.id.btnLeaderboard)
        btnLeaderBoard.setOnClickListener{
            val intent = Intent (this, LeaderBoard::class.java)
            startActivity(intent)
        }

    }
}