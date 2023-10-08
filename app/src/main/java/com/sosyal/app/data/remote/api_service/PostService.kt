package com.sosyal.app.data.remote.api_service

import android.util.Log
import com.sosyal.app.data.mapper.toPost
import com.sosyal.app.data.remote.dto.PostDto
import com.sosyal.app.domain.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import io.ktor.websocket.send
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

    private fun connectToWebSocket() {
        CoroutineScope(ioDispatcher).launch(coroutineExceptionHandler) {
            wsClient.wss(path = "/post") {
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

    suspend fun getPosts() = httpClient.get("/posts")

    fun receivePost(): SharedFlow<Post> {
        connectToWebSocket()
        return _post.asSharedFlow()
    }

    suspend fun sendPost(postDto: PostDto) {
        try {
            webSocketSession?.send(Json.encodeToString(postDto))
        } catch (e: Exception) {
            Log.d("Upload post error", e.message!!)
        }
    }

    suspend fun getPostDetail(id: String) = httpClient.get("/posts/$id")

    suspend fun deletePost(id: String) = httpClient.delete("/posts/$id")
}