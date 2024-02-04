package com.example.trivial.model

data class QuestionModel (
    val question : String,
    val answers : List<String>,
    val correctAnswer: String,
    val type : String,
    val difficulty: String

)


