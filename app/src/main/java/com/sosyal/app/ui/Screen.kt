package com.sosyal.app.ui

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
    object Register : Screen("register_screen")
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object Comments : Screen("comments_screen")
    object UploadEditPost : Screen("upload_edit_post_screen")
    object Profile : Screen("profile_screen")
    object EditProfile : Screen("edit_profile_screen")
}
