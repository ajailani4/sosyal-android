package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.repository.PostRepository

class GetPostsUseCase(private val postRepository: PostRepository) {
    operator fun invoke() = postRepository.getPosts()
}