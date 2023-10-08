package com.sosyal.app.data.remote.data_source

import com.sosyal.app.data.remote.api_service.CommentService
import com.sosyal.app.data.remote.dto.CommentDto

class CommentDataSource(private val commentService: CommentService) {
    suspend fun getComments(postId: String) = commentService.getComments(postId)

    fun receiveComment(postId: String) = commentService.receiveComment(postId)

    suspend fun sendComment(commentDto: CommentDto) {
        commentService.sendComment(commentDto)
    }
}