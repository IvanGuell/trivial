
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
    fun setCurrentQuestion(question: QuestionModel?) {
        _actualQuestion.value = question
    }
    private val _correctCounter = MutableLiveData<Int>().apply { value = 0 }
    val correctCounter: LiveData<Int> get() = _correctCounter

    private val _timerDuration = MutableLiveData<Int>().apply { value = 10 }
    val timerDuration: LiveData<Int> get() = _timerDuration

    private var _progress = MutableLiveData<Float>().apply { value = 1f }
    val progress: LiveData<Float> get() = _progress

    private val _score = MutableLiveData<Int>().apply { value = 0 }
    val score: LiveData<Int> get() = _score

    private var scoreMultiplier = 1.0

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

        val newQuestion = filteredQuestions.randomOrNull()

        // Actualizar el multiplicador según el nivel de dificultad
        scoreMultiplier = getScoreMultiplier()

        return newQuestion
    }

    fun incrementCorrectCounter() {
        _correctCounter.value = (_correctCounter.value ?: 0) + 1
        incrementScore()
    }

    fun resetCounters() {
        _correctCounter.value = 0
    }
    fun setTimerDuration(duration: Int) {
        _timerDuration.value = duration
    }

    fun subProgressBar (substract: Float){
        _progress.value = _progress.value?.minus(substract) ?: 1f
    }
    fun incrementScore() {
        val scoreToAdd = (2 * scoreMultiplier).toInt()
        _score.value = (_score.value ?: 0) + scoreToAdd
    }
    private fun getScoreMultiplier(): Double {
        return when (difficult) {
            "Fácil" -> 1.0
            "Medio" -> 1.5
            "Difícil" -> 2.0
            else -> 1.0 // Manejo por defecto, por si el nivel no coincide con ninguno de los anteriores
        }
    }
    fun resetScore() {
        _score.value = 0
    }
}