package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BMQuiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bmquiz)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val quiz1  = findViewById<Button>(R.id.Quiz1)
        quiz1.setOnClickListener{
            val intent = Intent ( this, ArahanBM::class.java)
            startActivity(intent)
        }

        val Quiz2  = findViewById<Button>(R.id.Quiz2)
        Quiz2.setOnClickListener{
            val intent = Intent ( this, ArahanBM2::class.java)
            startActivity(intent)
        }

        val Quiz3  = findViewById<Button>(R.id.Quiz3)
        Quiz3.setOnClickListener{
            val intent = Intent ( this, ArahanBM3::class.java)
            startActivity(intent)
        }

        val Quiz4  = findViewById<Button>(R.id.Quiz4)
        Quiz4.setOnClickListener{
            val intent = Intent ( this, ArahanBM4::class.java)
            startActivity(intent)
        }



    }
}