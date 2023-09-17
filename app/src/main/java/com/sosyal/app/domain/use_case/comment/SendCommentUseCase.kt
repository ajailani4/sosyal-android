package com.sosyal.app.domain.use_case.comment

import com.sosyal.app.domain.model.Comment
import com.sosyal.app.domain.repository.CommentRepository

class SendCommentUseCase(private val commentRepository: CommentRepository) {
    suspend operator fun invoke(comment: Comment) {
        commentRepository.sendComment(comment)
    }
}