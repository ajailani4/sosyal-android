package com.sosyal.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: String? = null,
    val participants: List<String>
)
