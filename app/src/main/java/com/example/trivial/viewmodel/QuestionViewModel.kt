package com.example.trivial.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
    fun setCurrentQuestion(question: QuestionModel?) {
        _actualQuestion.value = question
    }

    private val _correctCounter = MutableLiveData<Int>().apply { value = 0 }
    private val _timerDuration = MutableLiveData<Int>().apply { value = 10 }
    val timerDuration: LiveData<Int> get() = _timerDuration
    private var _progress = MutableLiveData<Float>().apply { value = 1f }
    private val _score = MutableLiveData<Int>().apply { value = 0 }
    val score: LiveData<Int> get() = _score
    private var scoreMultiplier = 1.0
    var colorModeOn by mutableStateOf(false)

    private val _correctAnswer = MutableLiveData<String?>()
    val correctAnswer: LiveData<String?> get() = _correctAnswer


    var difficult = "Fácil"
    var genre = "Todos"
    fun changeDiff(difficulty: String) {
        difficult = difficulty
    }

    fun changeGenre(genre: String) {
        this.genre = genre
    }

    fun setRounds(selectedRounds: Int) {
        _rounds.value = selectedRounds
    }

    fun getRandomQuestion(genre: String): QuestionModel? {

        val filteredQuestions = QuestionProvider.listaDePreguntas.filter {
            it.difficulty == difficult && (genre == "Todos" || it.type == genre)
        }

        val newQuestion = filteredQuestions.randomOrNull()

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

    fun subProgressBar(substract: Float) {
        _progress.value = _progress.value?.minus(substract) ?: 1f
    }

    private fun incrementScore() {
        val scoreToAdd = (2 * scoreMultiplier).toInt()
        _score.value = (_score.value ?: 0) + scoreToAdd
    }

    private fun getScoreMultiplier(): Double {
        return when (difficult) {
            "Fácil" -> 1.0
            "Medio" -> 1.5
            "Difícil" -> 2.0
            else -> 1.0
        }
    }

    fun resetScore() {
        _score.value = 0
    }

    fun changeColorMode(isOn: Boolean) {
        colorModeOn = isOn
    }

    fun answerSelected(){

    }

}