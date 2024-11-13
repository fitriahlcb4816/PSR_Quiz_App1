package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BMLesson : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmlesson)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val Lesson1 = findViewById<Button>(R.id.Lesson1)
        Lesson1.setOnClickListener{
            val intent = Intent ( this, BMLesson1::class.java)
            startActivity(intent)
        }

        val Lesson2 = findViewById<Button>(R.id.Lesson2)
        Lesson2.setOnClickListener{
            val intent = Intent ( this, BMLesson2::class.java)
            startActivity(intent)
        }

        val Lesson3 = findViewById<Button>(R.id.Lesson3)
        Lesson3.setOnClickListener{
            val intent = Intent ( this, BMLesson3::class.java)
            startActivity(intent)
        }

        val Lesson4 = findViewById<Button>(R.id.Lesson4)
        Lesson4.setOnClickListener{
            val intent = Intent ( this, BMLesson4::class.java)
            startActivity(intent)
        }

        val Lesson5 = findViewById<Button>(R.id.Lesson5)
        Lesson5.setOnClickListener{
            val intent = Intent ( this, BMLesson5::class.java)
            startActivity(intent)
        }

        val Lesson6 = findViewById<Button>(R.id.Lesson6)
        Lesson6.setOnClickListener{
            val intent = Intent ( this, BMLesson6::class.java)
            startActivity(intent)
        }


    }
}