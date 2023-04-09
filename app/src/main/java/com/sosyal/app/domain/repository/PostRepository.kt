package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.json.JsonObject

interface PostRepository {
    fun receivePost(): SharedFlow<Post>

    suspend fun sendPost(post: Post)

    fun getPostDetail(id: String): Flow<Resource<Post>>

    fun deletePost(id: String): Flow<Resource<JsonObject>>
}