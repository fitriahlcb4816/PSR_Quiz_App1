package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ArahanBM2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_arahan_bm2)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val StartBM1  = findViewById<Button>(R.id.StartBM1)
        StartBM1.setOnClickListener{
            val intent = Intent ( this, BMQuiz2::class.java)
            startActivity(intent)
        }
    }
}