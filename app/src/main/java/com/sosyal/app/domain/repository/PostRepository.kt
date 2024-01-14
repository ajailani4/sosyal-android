package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import com.sosyal.app.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.json.JsonObject

interface PostRepository {
    fun getPosts(): Flow<ApiResult<List<Post>>>

    fun receivePost(): SharedFlow<Post>

    suspend fun sendPost(post: Post)

    fun getPostDetail(id: String): Flow<ApiResult<Post>>

    fun deletePost(id: String): Flow<ApiResult<JsonObject>>
}