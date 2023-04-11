package com.sosyal.app.data.remote.data_source

import com.sosyal.app.data.remote.api_service.UserProfileService

class UserProfileRemoteDataSource(
    private val userProfileService: UserProfileService
) {
    suspend fun getUserProfile() = userProfileService.getUserProfile()
}