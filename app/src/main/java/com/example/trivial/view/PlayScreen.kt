
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
import androidx.navigation.NavController
import com.example.trivial.model.QuestionModel
import com.example.trivial.viewmodel.QuestionViewModel
import kotlinx.coroutines.delay
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.unit.max
import kotlin.concurrent.timer


@Composable
fun PlayScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    var difficulty by remember { mutableStateOf(questionViewModel.difficult) }
    var type by remember { mutableStateOf(questionViewModel.genre) }

    val actualQuestion by questionViewModel.actualQuestion.observeAsState()
    val rounds by questionViewModel.rounds.observeAsState()
    var currentRound by remember { mutableStateOf(0) }

    val correctCounter by questionViewModel.correctCounter.observeAsState()

    var pressedButton by remember { mutableStateOf(false) }
    var correct by remember { mutableStateOf(false) }

    val timerDuration by questionViewModel.timerDuration.observeAsState()
    var currentTime by remember { mutableStateOf(timerDuration?: 10)}


    val progress by questionViewModel.progress.observeAsState(1f)

    val score = questionViewModel.score.observeAsState()




    LaunchedEffect(questionViewModel) {
        questionViewModel.resetScore()
    }
    LaunchedEffect(actualQuestion) {
        currentTime = timerDuration?: 10

        while ( currentTime > 0) {
            delay(1000)
            currentTime--
            questionViewModel.subProgressBar(1f / currentTime)
            println("Current Time: $currentTime")
        }


        val newQuestion = questionViewModel.getRandomQuestion(type)
        questionViewModel.setCurrentQuestion(newQuestion)
        questionViewModel.resetCounters()
        currentRound++
        if (currentRound >= (rounds ?: 0)) {
            navController.navigate("result_screen")
        }
        questionViewModel.subProgressBar(1f)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Ronda $currentRound/${rounds ?: 0}",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(16.dp),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(text = "Puntuación: ${score.value}")



        println(" PREGUNTA ACTUAL: $actualQuestion")

        LaunchedEffect(difficulty, type) {
            val question = questionViewModel.getRandomQuestion(type)
            questionViewModel.setCurrentQuestion(question)
            println("XXXPREGUNTAXXX: $question")

            questionViewModel.resetCounters()

        }

        if (actualQuestion != null) {
            QuestionCard(actualQuestion!!)

            AnswerButtons(
                actualQuestion = actualQuestion,
                answers = actualQuestion!!.answers,
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
                        questionViewModel.setCurrentQuestion(newQuestion)
                    }
                }
            )
        } else {
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
                progress =  currentTime / (timerDuration?: 10).toFloat(),
                modifier = Modifier
                    .weight(1f)
                    .width(25.dp),
                color = Color.Blue,

            )
            CountdownTimer(currentTime)


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
    actualQuestion: QuestionModel?,
    onAnswerSelected: (Boolean) -> Unit
) {
    val shuffledAnswers = remember(actualQuestion?.answers) {
        shuffleAnswers(actualQuestion?.answers)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        shuffledAnswers.forEach { answer ->

            Button(
                onClick = {
                    val isCorrect = checkIfAnswerIsCorrect(answer, actualQuestion?.correctAnswer.orEmpty())
                    onAnswerSelected(isCorrect)

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

private fun shuffleAnswers(answers: List<String>?): List<String> {
    return answers?.shuffled() ?: emptyList()
}



fun checkIfAnswerIsCorrect(selectedAnswer: String, correctAnswer: String): Boolean {
    return selectedAnswer == correctAnswer
}

@Composable
fun CountdownTimer(currentTime: Int) {

    Text(
        text = "$currentTime s",
        modifier = Modifier
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}




