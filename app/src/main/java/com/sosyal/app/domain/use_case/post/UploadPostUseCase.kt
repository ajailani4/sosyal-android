package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.repository.PostRepository
import com.sosyal.app.util.Formatter
import java.util.*

class UploadPostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(
        username: String,
        content: String,
    ) = postRepository.uploadPost(
        Post(
            username = username,
            content = content,
            date = Formatter.convertDateToString(Date())
        )
    )
}