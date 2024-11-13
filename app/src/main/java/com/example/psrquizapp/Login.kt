package com.example.psrquizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.psrquizapp.databinding.ActivityLogin2Binding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLogin2Binding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener{
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            val Email = binding.Email.text.toString()
            val Password = binding.Password.text.toString()

            if (Email.isNotEmpty() && Password.isNotEmpty()){

                    firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener{
                        if (it.isSuccessful){

                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)

                        }
                    }
            }else{
                Toast.makeText(this, "Empty Fields Are Not Allowed" , Toast.LENGTH_SHORT).show()
            }
        }

        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show password
                binding.Password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                // Hide password
                binding.Password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            // Move cursor to the end
            binding.Password.setSelection(binding.Password.text?.length ?: 0)
        }

        val btnRegister = findViewById<Button>(R.id.btn_Register)
        btnRegister.setOnClickListener{
            val intent = Intent ( this, Register::class.java)
            startActivity(intent)
        }


    }

}

