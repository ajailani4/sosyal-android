package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Comment
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface CommentRepository {
    fun getComments(postId: String): Flow<Resource<List<Comment>>>

    fun receiveComment(postId: String): SharedFlow<Comment>

    suspend fun sendComment(comment: Comment)
}