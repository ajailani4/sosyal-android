package com.sosyal.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: String? = null,
    val username: String,
    val userAvatar: String? = null,
    val content: String,
    val likes: Int,
    val comments: Int,
    val date: String,
    val isEdited: Boolean? = null,
    val isLiked: Boolean? = null
)
