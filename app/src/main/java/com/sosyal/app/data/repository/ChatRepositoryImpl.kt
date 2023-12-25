package com.sosyal.app.data.repository

import android.content.Context
import com.sosyal.app.R
import com.sosyal.app.data.mapper.toChat
import com.sosyal.app.data.remote.data_source.ChatRemoteDataSource
import com.sosyal.app.data.remote.dto.ChatDto
import com.sosyal.app.data.remote.dto.response.BaseResponse
import com.sosyal.app.domain.repository.ChatRepository
import com.sosyal.app.util.Result
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.flow

class ChatRepositoryImpl(
    private val chatRemoteDataSource: ChatRemoteDataSource,
    private val context: Context
) : ChatRepository {
    override fun getChats() =
        flow {
            val response = chatRemoteDataSource.getChats()

            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.body<BaseResponse<List<ChatDto>>>()
                    emit(Result.Success(responseBody.data?.map { chatDto -> chatDto.toChat() }))
                }

                HttpStatusCode.InternalServerError -> emit(Result.Error(context.getString(R.string.server_error)))

                else -> emit(Result.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}