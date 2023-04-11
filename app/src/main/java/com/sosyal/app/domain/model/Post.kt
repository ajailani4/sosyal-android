package com.sosyal.app.domain.model

data class Post(
    val id: String? = null,
    val username: String? = null,
    val userAvatar: String? = null,
    val content: String? = null,
    val likes: Int = 0,
    val comments: Int = 0,
    val date: String? = null,
    val isEdited: Boolean? = null,
    val isLiked: Boolean? = null
)
