package com.sosyal.app.domain.model

data class Post(
    val id: String? = null,
    val username: String,
    val userAvatar: String? = null,
    val content: String,
    val likes: Int = 0,
    val comments: Int = 0,
    val date: String
)
