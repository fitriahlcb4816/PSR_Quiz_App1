package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InstructionMaths2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_instruction_maths2)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val StartM1  = findViewById<Button>(R.id.StartM1)
        StartM1.setOnClickListener{
            val intent = Intent ( this, MQuiz2::class.java)
            startActivity(intent)
        }

    }
}