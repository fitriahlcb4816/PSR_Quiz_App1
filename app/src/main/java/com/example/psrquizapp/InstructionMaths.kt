package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InstructionMaths : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_instruction_maths)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val StartM  = findViewById<Button>(R.id.StartM)
        StartM.setOnClickListener{
            val intent = Intent ( this, MQuiz1::class.java)
            startActivity(intent)
        }

    }
}