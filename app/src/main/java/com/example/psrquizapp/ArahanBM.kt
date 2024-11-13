package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ArahanBM : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_arahan_bm)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val StartBM  = findViewById<Button>(R.id.StartBM)
        StartBM.setOnClickListener{
            val intent = Intent ( this, BMQuiz1::class.java)
            startActivity(intent)
        }

    }
}