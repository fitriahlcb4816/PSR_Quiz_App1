package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InstructionMaths3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_instruction_maths3)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val StartM2  = findViewById<Button>(R.id.StartM2)
        StartM2.setOnClickListener{
            val intent = Intent ( this, MQuiz3::class.java)
            startActivity(intent)
        }

    }
}