package com.example.trivial.view

sealed class Routes(val route: String) {
    object SettingsScreen:Routes("settings_screen")
    object MenuScreen:Routes("menu_screen")
    object PlayScreen:Routes("play_screen")
    object ResultScreen:Routes("result_screen")

}
