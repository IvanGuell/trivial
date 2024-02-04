package com.example.trivial.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.trivial.viewmodel.QuestionViewModel

@Composable
fun MenuScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    var textFieldValue by remember { mutableStateOf("") }

    LaunchedEffect(questionViewModel) {
        // Este bloque se ejecutará cuando el componente se active
        questionViewModel.resetScore()
    }

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
            modifier = Modifier
                .padding(top = 46.dp),
            onClick = {
                navController.navigate("settings_screen")

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF051620)
            ),
            shape = RectangleShape
        ) {
            Text(
                text = "Ajustes",
                color = Color.White)
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .padding(top = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF051620)
                ),
                onClick = {
                    navController.navigate("play_screen")
                },
                shape = RectangleShape
            ) {
                Text(
                    text = "Jugar!",
                    color = Color.White
                )

            }
        }
    }
}







