package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface PostRepository {
    fun receivePost(): SharedFlow<Post>

    suspend fun uploadPost(post: Post)

    fun getPostDetail(): Flow<Resource<Post>>
}