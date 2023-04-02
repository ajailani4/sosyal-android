package com.sosyal.app.domain.use_case.auth

import com.sosyal.app.domain.repository.AuthRepository

class LoginAccountUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(
        username: String,
        password: String
    ) = authRepository.login(
        username = username,
        password = password
    )
}