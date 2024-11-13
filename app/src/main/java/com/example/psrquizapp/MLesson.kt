package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MLesson : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mlesson)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val LessonM1 = findViewById<Button>(R.id.LessonM1)
        LessonM1.setOnClickListener{
            val intent = Intent ( this, MLesson1::class.java)
            startActivity(intent)
        }

        val LessonM2 = findViewById<Button>(R.id.LessonM2)
        LessonM2.setOnClickListener{
            val intent = Intent ( this, MLesson2::class.java)
            startActivity(intent)
        }

        val LessonM3 = findViewById<Button>(R.id.LessonM3)
        LessonM3.setOnClickListener{
            val intent = Intent ( this, MLesson3::class.java)
            startActivity(intent)
        }

        val LessonM4 = findViewById<Button>(R.id.LessonM4)
        LessonM4.setOnClickListener{
            val intent = Intent ( this, MLesson4::class.java)
            startActivity(intent)
        }

        val LessonM5 = findViewById<Button>(R.id.LessonM5)
        LessonM5.setOnClickListener{
            val intent = Intent ( this, MLesson5::class.java)
            startActivity(intent)
        }

        val LessonM6 = findViewById<Button>(R.id.LessonM6)
        LessonM6.setOnClickListener{
            val intent = Intent ( this, MLesson6::class.java)
            startActivity(intent)
        }

    }
}