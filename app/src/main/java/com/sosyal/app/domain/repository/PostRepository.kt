package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import kotlinx.coroutines.flow.SharedFlow

interface PostRepository {
    fun getPost(): SharedFlow<Post>

    suspend fun submitPost(post: Post)
}