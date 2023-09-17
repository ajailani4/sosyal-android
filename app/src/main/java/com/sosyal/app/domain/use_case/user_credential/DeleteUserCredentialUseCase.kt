package com.sosyal.app.domain.use_case.user_credential

import com.sosyal.app.domain.repository.UserCredentialRepository

class DeleteUserCredentialUseCase(
    private val userCredentialRepository: UserCredentialRepository
) {
    suspend operator fun invoke() {
        userCredentialRepository.deleteUserCredential()
    }
}