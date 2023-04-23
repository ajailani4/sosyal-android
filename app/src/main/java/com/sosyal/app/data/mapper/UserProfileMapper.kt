package com.sosyal.app.data.mapper

import com.sosyal.app.data.remote.dto.UserProfileDto
import com.sosyal.app.domain.model.UserProfile

fun UserProfileDto.toUserProfile() =
    UserProfile(
        name = name,
        email = email,
        username = username,
        avatar = avatar
    )