package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Material : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)

    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val button5 = findViewById<Button>(R.id.button5)
        button5.setOnClickListener{
            val intent = Intent (this, BMLesson::class.java)
            startActivity(intent)
        }

        val button6 = findViewById<Button>(R.id.button6)
        button6.setOnClickListener{
            val intent = Intent (this, MLesson::class.java)
            startActivity(intent)
        }


    }
}