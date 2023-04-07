package com.sosyal.app.ui

sealed class Screen(val route: String) {
    object Register : Screen("register_screen")
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object UploadEditPost : Screen("upload_edit_post_screen")
}
