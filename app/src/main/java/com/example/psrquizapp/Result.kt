package com.example.psrquizapp

data class Result(
    val userId: String = "",
    val score: Int = 0,
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val timeTaken: Long = 0 // in seconds
)
