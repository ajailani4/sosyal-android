package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject
import java.io.File

interface UserProfileRepository {
    fun getUserProfile(): Flow<ApiResult<UserProfile>>
    fun editUserProfile(
        name: String,
        email: String,
        avatar: File?
    ): Flow<ApiResult<JsonObject>>
}