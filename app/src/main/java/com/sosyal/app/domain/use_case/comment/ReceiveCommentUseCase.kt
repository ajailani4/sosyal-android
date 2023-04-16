package com.sosyal.app.domain.use_case.comment

import com.sosyal.app.domain.repository.CommentRepository

class ReceiveCommentUseCase(private val commentRepository: CommentRepository) {
    operator fun invoke(postId: String) = commentRepository.receiveComment(postId)
}