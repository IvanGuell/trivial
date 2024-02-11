package com.example.trivial.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trivial.R
import com.example.trivial.viewmodel.QuestionViewModel

@Composable
fun SettingsScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    var difficulty by remember { mutableStateOf(questionViewModel.difficult) }
    var type by remember { mutableStateOf(questionViewModel.genre) }
    var show by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(
                    id = if (!questionViewModel.colorModeOn) R.drawable.claro else R.drawable.image
                ), contentScale = ContentScale.FillBounds
            )
            .scale(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        difficulty = difficultyDropDown(questionViewModel)
        type = typeDropDown(questionViewModel)

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Segundos por ronda",
            fontSize = 24.sp,
        )
        timerSeekBar(questionViewModel)

        Text(
            text = "Rondas",
            fontSize = 24.sp,
        )
        roundsRadioButton { selectedRounds ->
            questionViewModel.setRounds(selectedRounds)
        }

        Row(verticalAlignment = Alignment.CenterVertically)
        {
            Text(
                text = "Modo Oscuro",
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.width(32.dp))
            switchColorMode(questionViewModel)
        }


        Button(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .padding(top = 46.dp),
            onClick = { show = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            shape = RectangleShape
        ) {
            Text(
                "Ayuda",
                color = MaterialTheme.colorScheme.primary
            )
        }
        HelpDialog(show, { show = false }, { show = false })
        Button(
            modifier = Modifier
                .fillMaxWidth(0.4f),
            onClick = {
                navController.navigate("menu_screen")

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            shape = RectangleShape

        ) {
            Text(
                text = "Volver al menu",
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun difficultyDropDown(questionViewModel: QuestionViewModel): String {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(questionViewModel.difficult) }

    Column(
        modifier = Modifier
            .offset(y = (10).dp)
    ) {
        Box {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {
                    selectedText = it
                    questionViewModel.changeDiff(it)
                },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .clickable { expanded = true }
                    .width(300.dp),
                textStyle = TextStyle(
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                ),
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(300.dp)


            ) {
                listOf("Fácil", "Medio", "Difícil").forEach { difficulty ->
                    DropdownMenuItem(text = { Text(text = difficulty) }, onClick = {
                        expanded = false
                        selectedText = difficulty
                        questionViewModel.changeDiff(selectedText)
                    })
                }
            }
        }
    }
    return selectedText
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun typeDropDown(questionViewModel: QuestionViewModel): String {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(questionViewModel.genre) }

    Column(
        modifier = Modifier
            .offset(y = (10).dp)
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clickable { expanded = true }
                .width(300.dp),

            textStyle = TextStyle(
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            ),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(16.dp)
        ) {
            listOf(
                "Historia",
                "Geografía",
                "Entretenimiento",
                "Deportes",
                "Arte y literatura",
                "Todos"
            ).forEach { type ->
                DropdownMenuItem(text = { Text(text = type) }, onClick = {
                    expanded = false
                    selectedText = type
                    questionViewModel.changeGenre(selectedText)
                })
            }
        }
    }
    return selectedText
}

@Composable

fun HelpDialog(show: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit) {

    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("¿Como jugar?", fontSize = 20.sp) },
            text = {
                Text(
                    text = """
                        Bienvenido a "En-quiz-tados". El objetivo es responder correctamente las preguntas seleccionando una de las cuatro opciones disponibles.

                        Mecánicas del juego:
                        - Selecciona la dificultad entre Fácil, Normal o Difícil.
                        - Elige una categoría de preguntas entre Historia, Geografía, Entretenimiento, Deportes, Arte y literatura, o Todas.
                        - Adivina la respuesta correcta entre las opciones proporcionadas.
                        
                        Personaliza tu experiencia:
                        - Ajusta el temporizador deslizando el control deslizante.
                        - Selecciona la cantidad de rondas que deseas jugar: 5, 10 o 15.
                        - Activa o desactiva el modo oscuro según tus preferencias.

                               """.trimIndent(),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start

                )
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(
                        text = "Volver",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp
                    )
                }
            },
        )
    }
}


@Composable
fun roundsRadioButton(onRoundSelected: (Int) -> Unit) {
    val radioOptions = listOf(5, 10, 15)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }

    Column {
        radioOptions.forEach { rounds ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (rounds == selectedOption),
                        onClick = {
                            onOptionSelected(rounds)
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (rounds == selectedOption),
                    onClick = { onOptionSelected(rounds) }
                )
                Text(
                    text = rounds.toString(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        LaunchedEffect(selectedOption) {
            onRoundSelected(selectedOption)
        }
    }
}


@Composable
fun timerSeekBar(questionViewModel: QuestionViewModel) {
    val timerDuration by questionViewModel.timerDuration.observeAsState()

    Slider(
        value = timerDuration?.toFloat() ?: 10f,
        onValueChange = { value ->
            questionViewModel.setTimerDuration(value.toInt())
        },
        valueRange = 5f..20f,
        steps = 15,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.tertiary
        )
    )

    Text(
        text = "${timerDuration}s",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun switchColorMode(questionViewModel: QuestionViewModel) {
    var checked by remember { mutableStateOf(questionViewModel.colorModeOn) }

    Switch(
        checked = questionViewModel.colorModeOn,
        onCheckedChange = {
            checked = it
            questionViewModel.changeColorMode(it)
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color(0xFFDAE6F2),
            checkedTrackColor = Color(0x4001224C),
            uncheckedThumbColor = Color(0xFFDAE6F2),
            uncheckedTrackColor = Color(0x4001224C)
        ),
    )


}