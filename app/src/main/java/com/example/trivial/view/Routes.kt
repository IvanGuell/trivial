package com.example.trivial.view

sealed class Routes(val route: String) {
    object SplashScreen:Routes("splash_screen")
    object MenuScreen:Routes("menu_screen")
    object PlayScreen:Routes("play_screen")
    object ResultScreen:Routes("result_screen")

}
