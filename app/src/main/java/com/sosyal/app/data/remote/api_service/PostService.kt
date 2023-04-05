package com.sosyal.app.data.remote.api_service

import android.util.Log
import com.sosyal.app.BuildConfig
import com.sosyal.app.data.mapper.toPost
import com.sosyal.app.data.remote.dto.PostDto
import com.sosyal.app.domain.model.Post
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.http.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PostService(
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val _post = MutableSharedFlow<Post>()
    private var webSocketSession: DefaultWebSocketSession? = null

    init {
        connect()
    }

    private fun connect() {
        CoroutineScope(ioDispatcher).launch {
            httpClient.webSocket(path = "/post") {
                webSocketSession = this

                try {
                    incoming.consumeEach { postFrame ->
                        if (postFrame is Frame.Text) {
                            val postDto = Json.decodeFromString<PostDto>(postFrame.readText())

                            _post.emit(postDto.toPost())
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Get posts error", e.message!!)
                }
            }
        }
    }

    fun getPost() = _post.asSharedFlow()

    suspend fun submitPost(postDto: PostDto) {
        try {
            webSocketSession?.send(Json.encodeToString(postDto))
        } catch (e: Exception) {
            Log.d("Submit post error", e.message!!)
        }
    }
}