package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.repository.PostRepository

class SubmitPostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(post: Post) = postRepository.submitPost(post)
}