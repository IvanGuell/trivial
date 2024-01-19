package com.example.trivial.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    var difficulty by remember { mutableStateOf("Medio") }
    var type by remember { mutableStateOf("Todos") }
    var show by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        difficulty = difficultyDropDown()
        type = typeDropDown()
        Button(
            modifier = Modifier
                .padding(top = 46.dp),
            onClick = { show = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF051620)
            ),
            shape = RectangleShape
        ) {
            Text("Ayuda")
        }
        HelpDialog(show, { show = false }, { show = false })


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun difficultyDropDown(): String {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Dificultad") }

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
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(16.dp)


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


@Composable

fun HelpDialog(show: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit) {

    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("¿Como jugar?", fontSize = 20.sp) },
            text = {
                Text(
                    text = """
                              Se trata del ahorcado, 
                              las mecanicas son simples 
                              Solo debes introducir
                              letras hasta acertar la palabra.
                              
                              Dificultad: Fácil
                              Maximo 5 letras
                                  10 fallos
                                  
                              Dificultad: Normal
                              Maximo 8 letras
                                  10 fallos
                              
                              Dificultad: Dificil
                              Sin Maximo de letras
                                   5 fallos

                               """.trimIndent(),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start

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
fun typeDropDown(): String {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Generos") }

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
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(16.dp)


        ) {
            listOf("Historia", "Geografia", "Cine", "Todos").forEach { type ->
                DropdownMenuItem(text = { Text(text = type) }, onClick = {
                    expanded = false
                    selectedText = type
                })
            }
        }
    }
    return selectedText
}

