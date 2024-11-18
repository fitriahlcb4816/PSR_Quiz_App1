package com.example.psrquizapp

class QuizQuestion(private val questions: List<Question>) {

    private var currentQuestionIndex = 0
    private var score = 0

    fun getCurrentQuestion(): Question = questions[currentQuestionIndex]

    fun getCurrentQuestionIndex(): Int {
        return currentQuestionIndex
    }

    fun checkAnswer(selectedOptionIndex: Int): Boolean {
        val correct = getCurrentQuestion().correctAnswerIndex == selectedOptionIndex
        if (correct) score++
        currentQuestionIndex++
        return correct
    }

    fun getTotalQuestions(): Int = questions.size
    fun hasMoreQuestions(): Boolean = currentQuestionIndex < questions.size
    fun getScore(): Int = score
}