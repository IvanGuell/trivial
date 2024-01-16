package com.example.trivial.view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trivial.hangingLetter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {
    var textFieldValue by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("Medio") }
    var show by remember { mutableStateOf( false )}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = """Menu
                | 
                | 
                | 
                | de 
                |      
                |      
                |    inicio""".trimMargin(),
            fontSize = 84.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontFamily = hangingLetter,
            letterSpacing = 10.sp

        )
        Button(
            modifier = Modifier.offset(y = (560).dp),
            onClick = {show = true},
            shape = RectangleShape

        ) {
            Text("Ayuda",
                fontFamily = hangingLetter,
                fontSize = 44.sp

            )


        }
        cuadroAyuda(show, {show = false}, {show = false})


        var dificultad = dropDownDificultad()




        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = Modifier.offset(y = (260).dp).background(Color.Transparent),
                onClick = {
                    navController.navigate(Routes.PlayScreen.route)
                },
                shape = RectangleShape,

            ) {
                Text("Play",
                        fontFamily = hangingLetter,
                    fontWeight = FontWeight.SemiBold,

                    fontSize = 44.sp)

            }
        }
    }
}


@Composable

fun cuadroAyuda(show: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit) {

    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("¿Como jugar?", fontSize = 20.sp) },
            text = {
                Text(
                    text = "Se trata del juego el ahorcado, las mecanicas son simples, " +
                            "solo tienes que ir introduciendo letras hasta acertar la palabra " +
                            "o fallar mas de 10 veces!", fontSize = 20.sp
                )
            },


            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(
                        text = "Ok!",
                        fontSize = 20.sp
                    )
                }
            },

            )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownDificultad(): String {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Dificultad") }

    Column (
        modifier = Modifier
            .offset(y = (248).dp)


    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(16.dp)
        ) {
            listOf("Fácil", "Medio", "Difícil").forEach { difficulty ->
                DropdownMenuItem(text = { Text(text = difficulty) }, onClick = {
                    expanded = false
                    selectedText = difficulty
                })
            }
        }
    }
    return selectedText
}

