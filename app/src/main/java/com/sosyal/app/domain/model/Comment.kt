package com.sosyal.app.domain.model

data class Comment(
    val id: String? = null,
    val postId: String,
    val username: String,
    val userAvatar: String? = null,
    val content: String
)
