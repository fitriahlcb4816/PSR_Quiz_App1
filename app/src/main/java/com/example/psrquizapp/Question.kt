package com.example.psrquizapp

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val imageResId: Int? = null // Nullable, so only set when a question has an image

)