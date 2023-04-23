package com.sosyal.app.domain.use_case.auth

import com.sosyal.app.domain.repository.AuthRepository

class RegisterAccountUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(
        name: String,
        email: String,
        username: String,
        password: String
    ) = authRepository.register(
        name = name,
        email = email,
        username = username,
        password = password
    )
}