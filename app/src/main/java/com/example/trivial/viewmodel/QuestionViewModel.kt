package com.example.trivial.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivial.model.QuestionModel
import com.example.trivial.model.QuestionProvider

class QuestionViewModel : ViewModel() {
    private val _actualQuestion = MutableLiveData<QuestionModel?>()
    private val _rounds = MutableLiveData<Int>().apply { value = 10 }
    val actualQuestion: LiveData<QuestionModel?> get() = _actualQuestion
    val rounds: LiveData<Int> get() = _rounds
    fun setPreguntaActual(question: QuestionModel?) {
        _actualQuestion.value = question
    }

    fun nextRound(){


    }

    var difficult = "Fácil"
    var genre = "Todos"
    fun changeDiff(difficulty: String ){
         difficult = difficulty
    }

    fun changeGenre(genre: String) {
        this.genre = genre
    }
    fun setRounds(selectedRounds: Int) {
        _rounds.value = selectedRounds
    }
    fun getRandomQuestion(genre: String): QuestionModel? {
        Log.i("DIFICULTAD", difficult)
        Log.i("GENRE", genre)
        val filteredQuestions = QuestionProvider.listaDePreguntas.filter {
            it.difficulty == difficult && (genre == "Todos" || it.type == genre)
        }
        return filteredQuestions.randomOrNull()
    }
}