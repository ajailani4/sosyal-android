package com.sosyal.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.sosyal.app.ui.Navigation
import com.sosyal.app.ui.Screen
import com.sosyal.app.ui.screen.splash.SplashViewModel
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
                            val navController = rememberNavController()

                            Navigation(
                                navController = navController,
                                startDestination = startDestination
                            )
                        }
                    }
                }
            }
        }
    }
}