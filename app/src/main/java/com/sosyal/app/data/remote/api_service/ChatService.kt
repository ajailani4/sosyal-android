package com.sosyal.app.data.remote.api_service

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class ChatService(
    private val httpClient: HttpClient
) {
    suspend fun getChats() = httpClient.get("/chats")
}