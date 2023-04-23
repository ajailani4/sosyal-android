package com.sosyal.app.data.remote.data_source

import com.sosyal.app.data.remote.api_service.CommentService
import com.sosyal.app.data.remote.dto.CommentDto
import com.sosyal.app.domain.model.Comment
import kotlinx.coroutines.flow.SharedFlow

class CommentDataSource(private val commentService: CommentService) {
    fun receiveComment(postId: String) = commentService.receiveComment(postId)

    suspend fun sendComment(commentDto: CommentDto) {
        commentService.sendComment(commentDto)
    }
}