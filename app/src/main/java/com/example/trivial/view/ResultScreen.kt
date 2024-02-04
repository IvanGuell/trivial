package com.example.trivial.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.trivial.viewmodel.QuestionViewModel

@Composable
fun ResultScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    val score = questionViewModel.score.observeAsState(0).value ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Enhorabuena!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tu puntuación es: $score",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.height(32.dp))
        Row {
            Button(
                onClick = {
                    navController.navigate("play_screen")

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Volver a Jugar")
            }
            Button(
                onClick = {
                    navController.navigate("menu_screen")

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Volver al menu")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        ShareButton(text = "¡Obtuve una puntuación de $score en en-quiz-tados! ¿Puedes superarme?")

    }
}
@Composable
fun ShareButton(text: String) {
    val context = LocalContext.current
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    val shareIntent = Intent.createChooser(sendIntent, "Compartir con...")
    Button(
        onClick = {
            startActivity(context, shareIntent, null)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.White
        )
    ) {
        Icon(imageVector = Icons.Default.Share, contentDescription = "Compartir")
        Text("Compartir", modifier = Modifier.padding(start = 8.dp))
    }
}