package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InstructionMaths4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_instruction_maths4)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val StartM3  = findViewById<Button>(R.id.StartM3)
        StartM3.setOnClickListener{
            val intent = Intent ( this, MQuiz4::class.java)
            startActivity(intent)
        }
    }
}