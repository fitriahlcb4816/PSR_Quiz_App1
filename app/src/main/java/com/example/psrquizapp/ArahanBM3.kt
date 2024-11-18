package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ArahanBM3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_arahan_bm3)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val StartBM2  = findViewById<Button>(R.id.StartBM2)
        StartBM2.setOnClickListener{
            val intent = Intent ( this, BmQuiz3::class.java)
            startActivity(intent)
        }
    }
}