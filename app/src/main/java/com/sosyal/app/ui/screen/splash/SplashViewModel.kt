package com.sosyal.app.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase

class SplashViewModel(
    private val getUserCredentialUseCase: GetUserCredentialUseCase
) : ViewModel() {
    fun getUserCredential() = getUserCredentialUseCase()
}