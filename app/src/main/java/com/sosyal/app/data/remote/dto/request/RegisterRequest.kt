package com.sosyal.app.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val username: String,
    val password: String
)
