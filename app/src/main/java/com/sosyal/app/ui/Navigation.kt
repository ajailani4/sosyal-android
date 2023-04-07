package com.sosyal.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sosyal.app.ui.screen.home.HomeScreen
import com.sosyal.app.ui.screen.login.LoginScreen
import com.sosyal.app.ui.screen.register.RegisterScreen
import com.sosyal.app.ui.screen.upload_edit_post.UploadEditPostScreen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true

                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true

                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen()
        }

        composable(route = Screen.UploadEditPost.route) {
            UploadEditPostScreen()
        }
    }
}