package com.example.trivial.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivial.model.QuestionModel

class QuestionViewModel : ViewModel() {
    private val _actualQuestion = MutableLiveData<QuestionModel?>()
    private val _rounds = MutableLiveData<Int>()
    val actualQuestion: LiveData<QuestionModel?> get() = _actualQuestion
    val rounds: LiveData<Int> get() = _rounds
    fun setPreguntaActual(question: QuestionModel?) {
        _actualQuestion.value = question
    }

    fun nextRound(){


    }
    private val listaDePreguntas = listOf(
        QuestionModel(
            "¿Cuál es la capital de Francia?",
            listOf("Madrid", "Paris", "Londres", "Pepe"),
            "Paris",
            "Geografía",
            "Fácil"
        ),
        QuestionModel(
            "¿Quién fue el primer presidente de Estados Unidos?",
            listOf("George Washington", "Thomas Jefferson", "Abraham Lincoln", "Pepe"),
            "George Washington",
            "Historia",
            "Medio"
        ),
        QuestionModel(
            "¿Quién fue el director de Matrix?",
            listOf("Lana Wachowski", "Monica Blanchette", "Harry Styles", "Pepe"),
            "Lana Wachowski",
            "Cine",
            "Difícil"
        ),
    )
    var difficult = "Fácil"
    var genre = "Todos"
    fun changeDiff(difficulty: String ){
         difficult = difficulty
    }

    fun changeGenre(genre: String) {
        this.genre = genre
    }

    fun getRandomQuestion(genre: String): QuestionModel? {
        Log.i("DIFICULTAD", difficult)
        Log.i("GENRE", genre)
        val filteredQuestions = listaDePreguntas.filter {
            it.difficulty == difficult && (genre == "Todos" || it.type == genre)
        }
        return filteredQuestions.randomOrNull()
    }
}