package com.example.psrquizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

       val btnLogin = findViewById<Button>(R.id.btn_Login)
        btnLogin.setOnClickListener{
            val intent = Intent ( this, Home::class.java)
            startActivity(intent)
        }

        val btnRegister = findViewById<Button>(R.id.btn_Register)
        btnRegister.setOnClickListener{
            val intent = Intent (this, Register::class.java)
            startActivity(intent)
        }

    }

}
