package com.sosyal.app.data.repository

import com.sosyal.app.data.mapper.toCommentDto
import com.sosyal.app.data.remote.data_source.CommentDataSource
import com.sosyal.app.domain.model.Comment
import com.sosyal.app.domain.repository.CommentRepository
import kotlinx.coroutines.flow.SharedFlow

class CommentRepositoryImpl(
    private val commentDataSource: CommentDataSource
) : CommentRepository {
    override fun receiveComment(postId: String) = commentDataSource.receiveComment(postId)

    override suspend fun sendComment(comment: Comment) {
        commentDataSource.sendComment(comment.toCommentDto())
    }
}