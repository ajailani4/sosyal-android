package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Chat
import com.sosyal.app.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<ApiResult<List<Chat>>>
}