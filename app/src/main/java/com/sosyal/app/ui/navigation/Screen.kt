package com.sosyal.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector? = null
) {
    object Welcome : Screen("welcome_screen")
    object Register : Screen("register_screen")
    object Login : Screen("login_screen")
    object Comments : Screen("comments_screen")
    object Profile : Screen("profile_screen")
    object EditProfile : Screen("edit_profile_screen")

    // Bottom Navigation Bar Menu
    object Home : Screen(
        route = "home_screen",
        icon = Icons.Outlined.Home
    )

    object UploadEditPost : Screen(
        route = "upload_edit_post_screen",
        icon = Icons.Outlined.AddBox
    )

    object Chats : Screen(
        route = "chats_screen",
        icon = Icons.Outlined.Chat
    )
}
