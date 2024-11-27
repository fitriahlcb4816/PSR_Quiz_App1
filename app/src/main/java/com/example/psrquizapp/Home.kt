package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        val button = findViewById<Button>(R.id.material)
        button.setOnClickListener{
            val intent = Intent (this, Material::class.java)
            startActivity(intent)
        }

        val btnQuiz = findViewById<Button>(R.id.btnQuiz2)
        btnQuiz.setOnClickListener{
            val intent = Intent (this, Quiz::class.java)
            startActivity(intent)
        }

        val Profile = findViewById<Button>(R.id.Profile)
        Profile.setOnClickListener{
            val intent = Intent (this, StudentProfileActivity::class.java)
            startActivity(intent)
        }




        val btnLeaderBoard = findViewById<Button>(R.id.btnLeaderboard)
        btnLeaderBoard.setOnClickListener{
            val intent = Intent (this, LeaderBoardActivity::class.java)
            startActivity(intent)
        }

    }
}