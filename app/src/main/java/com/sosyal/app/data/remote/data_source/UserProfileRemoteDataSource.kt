package com.sosyal.app.data.remote.data_source

import com.sosyal.app.data.remote.api_service.UserProfileService
import java.io.File

class UserProfileRemoteDataSource(
    private val userProfileService: UserProfileService
) {
    suspend fun getUserProfile() = userProfileService.getUserProfile()

    suspend fun editUserProfile(
        name: String,
        email: String,
        avatar: File?
    ) = userProfileService.editUserProfile(
        name = name,
        email = email,
        avatar = avatar
    )
}