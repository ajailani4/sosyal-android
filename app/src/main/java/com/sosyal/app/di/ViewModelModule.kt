package com.sosyal.app.di

import com.sosyal.app.ui.screen.comments.CommentViewModel
import com.sosyal.app.ui.screen.edit_profile.EditProfileViewModel
import com.sosyal.app.ui.screen.home.HomeViewModel
import com.sosyal.app.ui.screen.login.LoginViewModel
import com.sosyal.app.ui.screen.profile.ProfileViewModel
import com.sosyal.app.ui.screen.register.RegisterViewModel
import com.sosyal.app.ui.screen.splash.SplashViewModel
import com.sosyal.app.ui.screen.upload_edit_post.UploadEditPostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get(), get(), get()) }
    viewModel { CommentViewModel(get(), get(), get(), get(), get()) }
    viewModel { UploadEditPostViewModel(get(), get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get(), get()) }
}