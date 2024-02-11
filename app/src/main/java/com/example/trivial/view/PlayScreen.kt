package com.example.trivial.view

import android.content.res.Configuration
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import com.example.trivial.R


@Composable
fun PlayScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    val difficulty by remember { mutableStateOf(questionViewModel.difficult) }
    val type by remember { mutableStateOf(questionViewModel.genre) }
    val actualQuestion by questionViewModel.actualQuestion.observeAsState()
    val rounds by questionViewModel.rounds.observeAsState()
    var currentRound by remember { mutableIntStateOf(0) }
    val timerDuration by questionViewModel.timerDuration.observeAsState()
    var currentTime by remember { mutableIntStateOf(timerDuration ?: 10) }
    val score = questionViewModel.score.observeAsState()

    LaunchedEffect(questionViewModel) {
        questionViewModel.resetScore()
    }
    LaunchedEffect(actualQuestion) {
        currentTime = timerDuration ?: 10

        while (currentTime > 0) {
            delay(1000)
            currentTime--
            questionViewModel.subProgressBar(1f / currentTime)
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
            .paint(
                painterResource(
                    id = if (!questionViewModel.colorModeOn) R.drawable.claro else R.drawable.image
                ), contentScale = ContentScale.FillBounds
            )
            .scale(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Ronda $currentRound/${rounds ?: 0}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
        Text(text = "Puntuación: ${score.value}")

        LaunchedEffect(difficulty, type) {
            val question = questionViewModel.getRandomQuestion(type)
            questionViewModel.setCurrentQuestion(question)
            questionViewModel.resetCounters()
        }

        if (actualQuestion != null) {
            QuestionCard(actualQuestion!!)

            AnswerButtons(
                actualQuestion = actualQuestion,
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
                progress = currentTime / (timerDuration ?: 10).toFloat(),
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
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .wrapContentSize(align = Alignment.Center),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = questionModel.question,
                )
            }
        }
    } else {
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
}

@Composable
fun AnswerButtons(
    actualQuestion: QuestionModel?,
    onAnswerSelected: (Boolean) -> Unit
) {
    val shuffledAnswers = remember(actualQuestion?.answers) {
        shuffleAnswers(actualQuestion?.answers)
    }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                shuffledAnswers.subList(0, 2).forEach { answer ->
                    Button(
                        onClick = {
                            val isCorrect = checkIfAnswerIsCorrect(
                                answer,
                                actualQuestion?.correctAnswer.orEmpty()
                            )
                            onAnswerSelected(isCorrect)
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text(
                            text = answer,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                shuffledAnswers.subList(2, 4).forEach { answer ->
                    Button(
                        onClick = {
                            val isCorrect = checkIfAnswerIsCorrect(
                                answer,
                                actualQuestion?.correctAnswer.orEmpty()
                            )
                            onAnswerSelected(isCorrect)
                        },
                        modifier = Modifier
                            .height(56.dp)
                            .weight(1f),
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
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            shuffledAnswers.forEach { answer ->

                Button(
                    onClick = {
                        val isCorrect =
                            checkIfAnswerIsCorrect(answer, actualQuestion?.correctAnswer.orEmpty())
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
}

private fun shuffleAnswers(answers: List<String>?): List<String> {
    return answers?.shuffled() ?: emptyList()
}

fun checkIfAnswerIsCorrect(selectedAnswer: String, correctAnswer: String): Boolean {
    return selectedAnswer == correctAnswer
}

@Composable
fun CountdownTimer(currentTime: Int) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Text(
            text = "$currentTime s",
            modifier = Modifier
                .padding(4.dp),
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            text = "$currentTime s",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}




