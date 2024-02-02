package com.example.trivial.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    private val _correctCounter = MutableLiveData<Int>().apply { value = 0 }
    val correctCounter: LiveData<Int> get() = _correctCounter

    private val _timerDuration = MutableLiveData<Int>().apply { value = 10 }
    val timerDuration: LiveData<Int> get() = _timerDuration

    private var _progress = MutableLiveData<Float>().apply { value = 1f }
    val progress: LiveData<Float> get() = _progress


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

    fun incrementCorrectCounter() {
        _correctCounter.value = (_correctCounter.value ?: 0) + 1
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
}