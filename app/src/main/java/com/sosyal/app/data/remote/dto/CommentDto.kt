package com.sosyal.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: String? = null,
    val postId: String,
    val username: String,
    val content: String
)
