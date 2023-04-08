package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.repository.PostRepository

/** Receive post from the Web Socket frame */
class ReceivePostUseCase(private val postRepository: PostRepository) {
    operator fun invoke() = postRepository.receivePost()
}