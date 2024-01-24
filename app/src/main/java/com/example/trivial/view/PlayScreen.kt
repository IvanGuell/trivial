package com.example.trivial.view

import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivial.model.QuestionModel
import com.example.trivial.viewmodel.QuestionViewModel

@Composable
fun PlayScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    var difficulty by remember { mutableStateOf(questionViewModel.difficult) }
    var type by remember { mutableStateOf(questionViewModel.genre) }

    val preguntaActual by questionViewModel.actualQuestion.observeAsState()
    println(" hola: $preguntaActual")

    LaunchedEffect(difficulty, type) {
        val question = questionViewModel.getRandomQuestion(type)
        questionViewModel.setPreguntaActual(question)
        println("HLDSHFSKDJF: $question")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (preguntaActual != null) {
            // Muestra la pregunta y sus respuestas
            QuestionCard(preguntaActual!!)
            AnswerButtons(preguntaActual!!.answers)
        } else {
            // Maneja el caso en que no haya pregunta disponible
            Text("No hay pregunta disponible para la selección actual.")
        }
    }
}


@Composable
fun QuestionCard(questionModel: QuestionModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = questionModel.question,
            )
        }
    }
}

@Composable
fun AnswerButtons(answers: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Muestra botones para cada respuesta
        answers.forEach { answer ->
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(

                )
            ) {
                Text(text = answer)
            }
        }
    }
}
