package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Chat
import com.sosyal.app.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<Result<List<Chat>>>
}