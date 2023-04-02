package com.sosyal.app.domain.use_case.user_credential

import com.sosyal.app.domain.repository.UserCredentialRepository

class SaveAccessTokenUseCase(
    private val userCredentialRepository: UserCredentialRepository
) {
    suspend operator fun invoke(accessToken: String) {
        userCredentialRepository.saveAccessToken(accessToken)
    }
}