package com.sosyal.app.data.remote.data_source

import com.sosyal.app.data.remote.api_service.AuthService
import com.sosyal.app.data.remote.dto.request.LoginRequest
import com.sosyal.app.data.remote.dto.request.RegisterRequest

class AuthRemoteDataSource(private val authService: AuthService) {
    suspend fun register(registerRequest: RegisterRequest) =
        authService.register(registerRequest)

    suspend fun login(loginRequest: LoginRequest) =
        authService.login(loginRequest)
}