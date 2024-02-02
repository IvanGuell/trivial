package com.example.trivial.view

import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.trivial.model.QuestionModel
import com.example.trivial.viewmodel.QuestionViewModel
import kotlinx.coroutines.delay
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.tooling.preview.Preview
import com.example.trivial.ui.theme.TrivialTheme


@Composable
fun PlayScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    var difficulty by remember { mutableStateOf(questionViewModel.difficult) }
    var type by remember { mutableStateOf(questionViewModel.genre) }

    val preguntaActual by questionViewModel.actualQuestion.observeAsState()
    val rounds by questionViewModel.rounds.observeAsState()
    var currentRound by remember { mutableStateOf(0) }

    val correctCounter by questionViewModel.correctCounter.observeAsState()

    var pressedButton by remember { mutableStateOf(false) }
    var correct by remember { mutableStateOf(false) }

    val timerDuration by questionViewModel.timerDuration.observeAsState()

    var timeRemaining by remember { mutableStateOf(timerDuration ?: 10) }

    val progress by questionViewModel.progress.observeAsState(1f)

    LaunchedEffect(timerDuration) {
        timeRemaining = timerDuration ?: 10
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Barra superior simulada con un Text
        Text(
            text = "Ronda $currentRound/${rounds ?: 0}",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(16.dp),
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        println("**********************************************************")
        println("**********************************************************")
        println(rounds)
        println(currentRound)
        println(correctCounter)

        println("**********************************************************")
        println("**********************************************************")


        println(" hola: $preguntaActual")
        LaunchedEffect(difficulty, type) {
            val question = questionViewModel.getRandomQuestion(type)
            questionViewModel.setPreguntaActual(question)
            println("HLDSHFSKDJF: $question")

            // Restablece correctCounter y correct al inicio de cada ronda
            questionViewModel.resetCounters()
        }

        if (preguntaActual != null) {
            // Muestra la pregunta y sus respuestas
            QuestionCard(preguntaActual!!)

            AnswerButtons(
                preguntaActual = preguntaActual,
                answers = preguntaActual!!.answers,
                correctCounter = correctCounter ?: 0,
                onAnswerSelected = { isCorrect ->
                    if (isCorrect) {
                        questionViewModel.incrementCorrectCounter()
                    }

                    currentRound++
                    if (currentRound >= (rounds ?: 0)) {
                        navController.navigate("result_screen")
                    } else {
                        // Pide una nueva pregunta
                        val newQuestion = questionViewModel.getRandomQuestion(type)
                        questionViewModel.setPreguntaActual(newQuestion)
                    }
                }
            )
        } else {
            // Maneja el caso en que no haya pregunta disponible
            Text("No hay pregunta disponible para la selección actual.")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                progress =  (progress),
                modifier = Modifier
                    .weight(1f)
                    .width(25.dp),
                color = Color.Blue
            )
            CountdownTimer(
                timeRemaining = timeRemaining,
                onTimerFinish = {
                    val newQuestion = questionViewModel.getRandomQuestion(type)
                    questionViewModel.setPreguntaActual(newQuestion)
                    questionViewModel.resetCounters()

                    timeRemaining = timerDuration ?: 10

                },
                questionViewModel
            )


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
fun AnswerButtons(
    answers: List<String>,
    correctCounter: Int,
    preguntaActual: QuestionModel?,
    onAnswerSelected: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        answers.forEach { answer ->
            Button(
                onClick = {
                    val isCorrect = checkIfAnswerIsCorrect(answer, preguntaActual?.correctAnswer.orEmpty())
                    onAnswerSelected(isCorrect)
                    println("-----------------------------------------------")
                    println(isCorrect)
                    println("-----------------------------------------------")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text = answer,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}


// Función de ejemplo para verificar si la respuesta es correcta
fun checkIfAnswerIsCorrect(selectedAnswer: String, correctAnswer: String): Boolean {
    return selectedAnswer == correctAnswer
}

@Composable
fun CountdownTimer(timeRemaining: Int, onTimerFinish: () -> Unit,  questionViewModel: QuestionViewModel) {
    var currentTime by remember { mutableStateOf(timeRemaining) }
    var substract by remember { mutableStateOf(1f / currentTime)}

    LaunchedEffect(currentTime) {
        while (currentTime > 0) {
            delay(1000)
            currentTime--
            questionViewModel.subProgressBar(substract ?: 0f)
            println("Current Time: $currentTime") // Add this line
        }

        // Se ejecuta cuando el temporizador llega a cero
        onTimerFinish()
        questionViewModel.subProgressBar(substract)
    }

    Text(
        text = "$currentTime s",
        modifier = Modifier
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}




