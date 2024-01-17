package com.example.trivial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivial.model.QuestionModel

class QuestionViewModel : ViewModel() {
    private val _preguntaActual = MutableLiveData<QuestionModel>()
    val preguntaActual: LiveData<QuestionModel> get() = _preguntaActual

    private val listaDePreguntas = listOf(
        QuestionModel(
            "¿Cuál es la capital de Francia?",
            listOf("Madrid", "París", "Londres"),
            "Geografía",
            "Fácil"
        ),
        QuestionModel(
            "¿Quién fue el primer presidente de Estados Unidos?",
            listOf("George Washington", "Thomas Jefferson", "Abraham Lincoln"),
            "Historia",
            "Medio"
        ),
        QuestionModel(
            "¿Quién fue el director de Matrix?",
            listOf("Lana Wachowski", "Monica Blanchette", "Harry Styles"),
            "Cine",
            "Dificil"
        ),
    )
}