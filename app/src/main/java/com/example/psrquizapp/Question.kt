package com.example.psrquizapp

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)