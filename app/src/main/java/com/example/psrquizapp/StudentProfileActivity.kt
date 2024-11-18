package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StudentProfileActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var gradeEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_student_profile)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        nameEditText = findViewById(R.id.nameEditText)
        ageEditText = findViewById(R.id.ageEditText)
        gradeEditText = findViewById(R.id.gradeEditText)
        saveButton = findViewById(R.id.saveButton)

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Load existing profile if available
        loadProfile()

        // Save profile on button click
        saveButton.setOnClickListener {
            saveProfile()
        }

        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener{
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
        }

    }

    private fun loadProfile() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("profiles").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val profile = document.toObject(StudentProfile::class.java)
                        profile?.let {
                            nameEditText.setText(it.name)
                            ageEditText.setText(it.age.toString())
                            gradeEditText.setText(it.grade)
                        }
                    } else {
                        Toast.makeText(this, "No profile found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error loading profile: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun saveProfile() {
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString().toIntOrNull() ?: 0
        val grade = gradeEditText.text.toString()

        // Validate input
        if (name.isBlank() || grade.isBlank()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = auth.currentUser?.uid
        if (userId != null) {
            val profile = StudentProfile(name, age, grade)

            db.collection("profiles").document(userId)
                .set(profile)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error saving profile: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }


}