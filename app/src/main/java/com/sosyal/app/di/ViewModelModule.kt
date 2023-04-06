package com.sosyal.app.di

import com.sosyal.app.ui.screen.home.HomeViewModel
import com.sosyal.app.ui.screen.login.LoginViewModel
import com.sosyal.app.ui.screen.register.RegisterViewModel
import com.sosyal.app.ui.screen.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
}