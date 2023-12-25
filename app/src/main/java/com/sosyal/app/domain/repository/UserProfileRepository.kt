package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject
import java.io.File

interface UserProfileRepository {
    fun getUserProfile(): Flow<Result<UserProfile>>
    fun editUserProfile(
        name: String,
        email: String,
        avatar: File?
    ): Flow<Result<JsonObject>>
}