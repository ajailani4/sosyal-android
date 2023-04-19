package com.sosyal.app.util

import com.sosyal.app.data.remote.dto.UserCredentialDto
import com.sosyal.app.domain.model.UserCredential

val userCredential = UserCredential(
    accessToken = "a1b2c3",
    username = "george_z"
)

val userCredentialDto = UserCredentialDto(
    accessToken = "a1b2c3",
    username = "george"
)