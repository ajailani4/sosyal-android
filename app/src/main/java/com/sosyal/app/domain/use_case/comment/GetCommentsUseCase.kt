package com.sosyal.app.domain.use_case.comment

import com.sosyal.app.domain.repository.CommentRepository

class GetCommentsUseCase(private val commentRepository: CommentRepository) {
    operator fun invoke(postId: String) = commentRepository.getComments(postId)
}