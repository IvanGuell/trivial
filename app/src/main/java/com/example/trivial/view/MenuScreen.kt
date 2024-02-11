package com.example.trivial.view


import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trivial.R
import com.example.trivial.viewmodel.QuestionViewModel


@Composable
fun MenuScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    LaunchedEffect(questionViewModel) { questionViewModel.resetScore() }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(
                    id = if (!questionViewModel.colorModeOn) R.drawable.claro else R.drawable.image
                ), contentScale = ContentScale.FillBounds
            )
            .scale(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = """En
                | 
                | 
                | quiz 
                |      
                |      
                |tados""".trimMargin(),
            fontSize = 60.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            fontFamily = hangingLetter,
            letterSpacing = 2.sp
        )

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.25f),
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
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.3f),
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
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        } else {
            Button(
                modifier = Modifier
                    .padding(46.dp)
                    .fillMaxWidth(0.4f),
                onClick = {
                    navController.navigate("settings_screen")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground
                ),
                shape = RectangleShape
            ) {
                Text(
                    text = "Ajustes",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.3f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground
                ),
                onClick = {
                    navController.navigate("play_screen")
                },
                shape = RectangleShape
            ) {
                Text(
                    text = "Jugar!",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}




