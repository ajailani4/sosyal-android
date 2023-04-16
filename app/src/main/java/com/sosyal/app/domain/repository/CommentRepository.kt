package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Comment
import kotlinx.coroutines.flow.SharedFlow

interface CommentRepository {
    fun receiveComment(postId: String): SharedFlow<Comment>

    suspend fun sendComment(comment: Comment)
}