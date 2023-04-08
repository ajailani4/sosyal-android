package com.sosyal.app.data.remote.api_service

import android.util.Log
import com.sosyal.app.data.mapper.toPost
import com.sosyal.app.data.remote.dto.PostDto
import com.sosyal.app.domain.model.Post
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PostService(
    private val wsClient: HttpClient,
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val _post = MutableSharedFlow<Post>()
    private var webSocketSession: DefaultWebSocketSession? = null

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine exception", throwable.message!!)
    }

    init {
        connectToWebSocket()
    }

    private fun connectToWebSocket() {
        CoroutineScope(ioDispatcher).launch(coroutineExceptionHandler) {
            wsClient.webSocket(path = "/post") {
                webSocketSession = this

                try {
                    incoming.consumeEach { postFrame ->
                        if (postFrame is Frame.Text) {
                            val postDto = Json.decodeFromString<PostDto>(postFrame.readText())

                            _post.emit(postDto.toPost())
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Get posts error", e.message!!)
                }
            }
        }
    }

    fun receivePost() = _post.asSharedFlow()

    suspend fun uploadPost(postDto: PostDto) {
        try {
            webSocketSession?.send(Json.encodeToString(postDto))
        } catch (e: Exception) {
            Log.d("Upload post error", e.message!!)
        }
    }

    suspend fun getPostDetail(id: String) = httpClient.get("/post/$id")
}