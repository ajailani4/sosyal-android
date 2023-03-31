package com.sosyal.app.ui

sealed class Screen(val route: String) {
    object Register : Screen("register_screen")
}
