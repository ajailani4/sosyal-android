package com.sosyal.app.data.remote.api_service

import com.sosyal.app.data.remote.dto.request.RegisterRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthService(private val httpClient: HttpClient) {
    suspend fun register(registerRequest: RegisterRequest) =
        httpClient.post("register") {
            contentType(ContentType.Application.Json)
            setBody(registerRequest)
        }
}