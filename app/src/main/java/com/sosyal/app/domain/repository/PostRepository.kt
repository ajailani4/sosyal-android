package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import com.sosyal.app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.json.JsonObject

interface PostRepository {
    fun getPosts(): Flow<Result<List<Post>>>

    fun receivePost(): SharedFlow<Post>

    suspend fun sendPost(post: Post)

    fun getPostDetail(id: String): Flow<Result<Post>>

    fun deletePost(id: String): Flow<Result<JsonObject>>
}