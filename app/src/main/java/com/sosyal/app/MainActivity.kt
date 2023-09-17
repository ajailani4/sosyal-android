package com.sosyal.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sosyal.app.ui.navigation.Navigation
import com.sosyal.app.ui.navigation.Screen
import com.sosyal.app.ui.screen.splash.SplashViewModel
import com.sosyal.app.ui.theme.Grey
import com.sosyal.app.ui.theme.NoRippleTheme
import com.sosyal.app.ui.theme.SosyalTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        lifecycleScope.launch {
            splashViewModel.getUserCredential().first().let { userCredential ->
                val startDestination = if (userCredential.accessToken != "") {
                    Screen.Home.route
                } else {
                    Screen.Welcome.route
                }

                setContent {
                    SosyalTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            Content(startDestination)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Content(startDestination: String) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomNavBarScreens = listOf(
        Screen.Home,
        Screen.UploadEditPost,
        Screen.Chats
    )

    Scaffold(
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                if (bottomNavBarScreens.any { it.route == currentRoute }) {
                    BottomAppBar(
                        backgroundColor = MaterialTheme.colors.background
                    ) {
                        bottomNavBarScreens.forEach { screen ->
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        imageVector = screen.icon ?: Icons.Outlined.Menu,
                                        contentDescription = screen.route
                                    )
                                },
                                selected = currentRoute == screen.route,
                                unselectedContentColor = Grey,
                                selectedContentColor = MaterialTheme.colors.primary,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Home.route) {
                                            saveState = true
                                        }

                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
            Navigation(
                navController = navController,
                startDestination = startDestination
            )
        }
    }
}