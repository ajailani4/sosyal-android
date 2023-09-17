package com.sosyal.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sosyal.app.ui.screen.comments.CommentsScreen
import com.sosyal.app.ui.screen.edit_profile.EditProfileScreen
import com.sosyal.app.ui.screen.home.HomeScreen
import com.sosyal.app.ui.screen.login.LoginScreen
import com.sosyal.app.ui.screen.profile.ProfileScreen
import com.sosyal.app.ui.screen.register.RegisterScreen
import com.sosyal.app.ui.screen.upload_edit_post.UploadEditPostScreen
import com.sosyal.app.ui.screen.welcome.WelcomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

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

                        popUpTo(Screen.Welcome.route) {
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

                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.Comments.route + "?postId={postId}",
            arguments = listOf(
                navArgument("postId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            CommentsScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToEditProfile = {
                    navController.navigate(Screen.EditProfile.route)
                },
                onNavigateToWelcome ={
                    navController.navigate(Screen.Welcome.route) {
                        launchSingleTop = true

                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.EditProfile.route) {
            EditProfileScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }

        // Bottom Nav Menu
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToComment = { postId ->
                    navController.navigate(Screen.Comments.route + "?postId=$postId")
                },
                onNavigateToUploadEditPost = { postId ->
                    navController.navigate(Screen.UploadEditPost.route + "?postId=$postId")
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(
            route = Screen.UploadEditPost.route + "?postId={postId}",
            arguments = listOf(
                navArgument("postId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            UploadEditPostScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.Chats.route) {

        }
    }
}