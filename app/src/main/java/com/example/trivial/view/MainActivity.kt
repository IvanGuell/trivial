package com.example.trivial.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
            TrivialTheme(true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.MenuScreen.route
                    ) {
                        composable(Routes.MenuScreen.route) { MenuScreen(navController = navigationController) }
                        composable(Routes.PlayScreen.route) { PlayScreen(navController = navigationController) }
                        composable(Routes.SettingsScreen.route) { SettingsScreen(navController = navigationController) }
                        composable(Routes.ResultScreen.route) { ResultScreen(navController = navigationController) }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrivialTheme {
        Greeting("Android")
    }
}