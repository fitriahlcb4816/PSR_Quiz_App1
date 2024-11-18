package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ArahanBM4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_arahan_bm4)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val StartBM3  = findViewById<Button>(R.id.StartBM3)
        StartBM3.setOnClickListener{
            val intent = Intent ( this, BMQuiz4::class.java)
            startActivity(intent)
        }
    }
}