package com.example.trivial.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trivial.R
import com.example.trivial.ui.theme.TrivialTheme
import com.example.trivial.viewmodel.QuestionViewModel

var hangingLetter = FontFamily(Font(R.font.hangingframes2))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val questionViewModel by viewModels<QuestionViewModel>()
        super.onCreate(savedInstanceState)
        setContent {
            TrivialTheme(
                darkTheme = questionViewModel.colorModeOn,
                content = {
                    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)

                    ){
                        val navigationController = rememberNavController()

                        NavHost(
                            navController = navigationController,
                            startDestination = Routes.MenuScreen.route
                        ) {
                            composable(Routes.MenuScreen.route) { MenuScreen(navController = navigationController, questionViewModel) }
                            composable(Routes.PlayScreen.route) { PlayScreen(navController = navigationController, questionViewModel) }
                            composable(Routes.SettingsScreen.route) { SettingsScreen(navController = navigationController, questionViewModel) }
                            composable(Routes.ResultScreen.route) { ResultScreen(navController = navigationController, questionViewModel) }
                        }
                    }


                }
            )
        }
    }
}


