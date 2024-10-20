package com.example.psrquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.psrquizapp.databinding.ActivityRegister2Binding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegister2Binding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegister2Binding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener{
            val Email = binding.Email.text.toString()
            val Password = binding.Password.text.toString()
            val ConfirmPassword = binding.ConfirmPassword.text.toString()

            if (Email.isNotEmpty() && Password.isNotEmpty()){
                if(Password == ConfirmPassword) {

                   firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener{
                        if (it.isSuccessful){

                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)

                        }
                   }

                }else{
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty Fields Are Not Allowed" , Toast.LENGTH_SHORT).show()
            }
        }

        val btnSignIn = findViewById<Button>(R.id.btn_signIn)
        btnSignIn.setOnClickListener{
            val intent = Intent ( this, Login::class.java)
            startActivity(intent)
        }

    }
}