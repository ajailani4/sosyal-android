package com.sosyal.app.domain.use_case.comment

import com.sosyal.app.domain.model.Comment
import com.sosyal.app.domain.repository.CommentRepository

class SendCommentUseCase(private val commentRepository: CommentRepository) {
    suspend operator fun invoke(
        postId: String,
        username: String,
        content: String
    ) {
        commentRepository.sendComment(
            Comment(
                postId = postId,
                username = username,
                content = content
            )
        )
    }
}