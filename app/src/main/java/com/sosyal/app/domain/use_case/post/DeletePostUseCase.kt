package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.repository.PostRepository

class DeletePostUseCase(private val postRepository: PostRepository) {
    operator fun invoke(id: String) = postRepository.deletePost(id)
}