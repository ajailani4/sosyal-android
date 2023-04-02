package com.sosyal.app.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.sosyal.app.domain.use_case.user_credential.GetAccessTokenUseCase

class SplashViewModel(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {
    fun getAccessToken() = getAccessTokenUseCase()
}