package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.repository.PostRepository
import com.sosyal.app.util.Formatter
import java.util.*

class SendPostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(post: Post) {
        postRepository.sendPost(post)
    }
}