package com.example.psrquizapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class LeaderBoardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val scores = mutableListOf<UserScore>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board2)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.leaderboardRecyclerView)
        leaderboardAdapter = LeaderboardAdapter(scores)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = leaderboardAdapter

        // Add some dummy data
        loadDummyData()
    }


    private fun loadDummyData() {
        scores.clear()
        scores.add(UserScore("Nazirah", 10000))
        scores.add(UserScore("Mizah", 9000))
        scores.add(UserScore("Ayu", 8000))
        scores.add(UserScore("Wafiq", 7000))
        scores.add(UserScore("Fitri", 6000))

        scores.sortByDescending { it.score } // Sort in descending order
        leaderboardAdapter.notifyDataSetChanged()
    }

    fun addScore(username: String, score: Int) {
        scores.add(UserScore(username, score))
        scores.sortByDescending { it.score } // Sort in descending order
        leaderboardAdapter.notifyDataSetChanged()
    }
}

