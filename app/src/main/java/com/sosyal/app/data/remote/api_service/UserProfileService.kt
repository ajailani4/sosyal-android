package com.sosyal.app.data.remote.api_service

import io.ktor.client.*
import io.ktor.client.request.*

class UserProfileService(private val httpClient: HttpClient) {
    suspend fun getProfile() = httpClient.get("/profile")
}