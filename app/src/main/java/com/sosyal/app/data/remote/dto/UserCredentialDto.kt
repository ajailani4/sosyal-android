package com.sosyal.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentialDto(
    val accessToken: String
)
