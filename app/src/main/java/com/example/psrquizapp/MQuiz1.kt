package com.example.psrquizapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.psrquizapp.databinding.ActivityMquiz1Binding

class MQuiz1 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mquiz1)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }
}