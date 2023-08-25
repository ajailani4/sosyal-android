package com.sosyal.app.data.remote.api_service

import android.util.Log
import com.sosyal.app.data.mapper.toComment
import com.sosyal.app.data.remote.dto.CommentDto
import com.sosyal.app.domain.model.Comment
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CommentService(
    private val wsClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val _comment = MutableSharedFlow<Comment>()
    private var webSocketSession: DefaultWebSocketSession? = null

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine exception", throwable.message!!)
    }

    private fun connectToWebSocket(postId: String) {
        CoroutineScope(ioDispatcher).launch(coroutineExceptionHandler) {
            wsClient.webSocket(path = "comment?postId=$postId") {
                webSocketSession = this

                try {
                    incoming.consumeEach { commentFrame ->
                        if (commentFrame is Frame.Text) {
                            val commentDto = Json.decodeFromString<CommentDto>(commentFrame.readText())

                            _comment.emit(commentDto.toComment())
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Get comments error", e.message!!)
                }
            }
        }
    }

    fun receiveComment(postId: String): SharedFlow<Comment> {
        connectToWebSocket(postId)
        return _comment.asSharedFlow()
    }

    suspend fun sendComment(commentDto: CommentDto) {
        try {
            webSocketSession?.send(Json.encodeToString(commentDto))
        } catch (e: Exception) {
            Log.d("Upload comment error", e.message!!)
        }
    }
}