package com.example.psrquizapp

import LeaderboardAdapter
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

    private lateinit var leaderboardRecyclerView: RecyclerView
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val leaderboardData = mutableListOf<StudentPerformance>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board2)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView)
        leaderboardRecyclerView.layoutManager = LinearLayoutManager(this)
        leaderboardAdapter = LeaderboardAdapter(leaderboardData)
        leaderboardRecyclerView.adapter = leaderboardAdapter

        loadLeaderboardData()
    }

    private fun loadLeaderboardData() {
        val db = FirebaseFirestore.getInstance()

        db.collection("leaderboard")
            .orderBy("score", Query.Direction.DESCENDING)
            .orderBy("timeTaken", Query.Direction.ASCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { documents ->
                leaderboardData.clear()
                for (document in documents) {
                    val performance = document.toObject(StudentPerformance::class.java)
                    leaderboardData.add(performance)
                }
                leaderboardAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.e("Leaderboard", "Failed to load leaderboard: ${e.message}")
                Toast.makeText(this, "Failed to load leaderboard", Toast.LENGTH_LONG).show()
            }
    }
}
