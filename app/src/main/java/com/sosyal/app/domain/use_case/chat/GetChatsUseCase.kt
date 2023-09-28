package com.sosyal.app.domain.use_case.chat

import com.sosyal.app.domain.repository.ChatRepository

class GetChatsUseCase(private val chatRepository: ChatRepository) {
    operator fun invoke() = chatRepository.getChats()
}