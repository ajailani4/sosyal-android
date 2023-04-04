package com.sosyal.app.domain.model

data class Post(
    val id: String,
    val username: String,
    val userAvatar: String? = null,
    val content: String,
    val likes: Int,
    val comments: Int,
    val date: String
)