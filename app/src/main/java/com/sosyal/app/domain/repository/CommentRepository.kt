package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Comment
import com.sosyal.app.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface CommentRepository {
    fun getComments(postId: String): Flow<ApiResult<List<Comment>>>

    fun receiveComment(postId: String): SharedFlow<Comment>

    suspend fun sendComment(comment: Comment)
}