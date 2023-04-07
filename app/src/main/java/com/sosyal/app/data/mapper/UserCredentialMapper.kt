package com.sosyal.app.data.mapper

import com.sosyal.app.data.remote.dto.UserCredentialDto
import com.sosyal.app.domain.model.UserCredential

fun UserCredentialDto.toUserCredential() =
    UserCredential(
        accessToken = accessToken,
        username = username
    )