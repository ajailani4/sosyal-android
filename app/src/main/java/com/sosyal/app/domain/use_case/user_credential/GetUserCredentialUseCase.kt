package com.sosyal.app.domain.use_case.user_credential

import com.sosyal.app.domain.repository.UserCredentialRepository

class GetUserCredentialUseCase(
    private val userCredentialRepository: UserCredentialRepository
) {
    operator fun invoke() = userCredentialRepository.getUserCredential()
}