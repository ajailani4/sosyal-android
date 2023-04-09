package com.sosyal.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    val id: String,
    val name: String,
    val email: String,
    val username: String,
    val avatar: String? = null
)
