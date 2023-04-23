package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.repository.PostRepository

class GetPostDetailUseCase(private val postRepository: PostRepository) {
    operator fun invoke(id: String) = postRepository.getPostDetail(id)
}