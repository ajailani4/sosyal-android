package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.repository.PostRepository
import com.sosyal.app.util.Formatter
import java.util.*

class SendPostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(
        id: String? = null,
        username: String,
        content: String,
        likes: Int = 0,
        comments: Int = 0,
        date: String? = null,
        isEdited: Boolean? = null,
        isLiked: Boolean? = null
    ) {
        postRepository.sendPost(
            Post(
                id = id,
                username = username,
                content = content,
                likes = likes,
                comments = comments,
                date = date ?: Formatter.convertDateToString(Date()),
                isEdited = isEdited,
                isLiked = isLiked
            )
        )
    }
}