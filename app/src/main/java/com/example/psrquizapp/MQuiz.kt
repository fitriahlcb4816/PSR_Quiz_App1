package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MQuiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mquiz)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val QuizM1  = findViewById<Button>(R.id.QuizM1)
        QuizM1.setOnClickListener{
            val intent = Intent ( this, InstructionMaths::class.java)
            startActivity(intent)
        }

        val QuizM2  = findViewById<Button>(R.id.QuizM2)
        QuizM2.setOnClickListener{
            val intent = Intent ( this, InstructionMaths2::class.java)
            startActivity(intent)
        }

        val QuizM3  = findViewById<Button>(R.id.QuizM3)
        QuizM3.setOnClickListener{
            val intent = Intent ( this, InstructionMaths3::class.java)
            startActivity(intent)
        }

        val QuizM4  = findViewById<Button>(R.id.QuizM4)
        QuizM4.setOnClickListener{
            val intent = Intent ( this, InstructionMaths4::class.java)
            startActivity(intent)
        }


    }
}