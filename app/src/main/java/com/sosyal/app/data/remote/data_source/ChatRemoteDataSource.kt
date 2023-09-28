package com.sosyal.app.data.remote.data_source

import com.sosyal.app.data.remote.api_service.ChatService

class ChatRemoteDataSource(private val chatService: ChatService) {
    suspend fun getChats() = chatService.getChats()
}