package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.JsonObject
import java.io.File

class UserProfileRepositoryFake : UserProfileRepository {
    private lateinit var resourceType: ResourceType

    override fun getUserProfile(): Flow<Result<UserProfile>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Result.Success(userProfile))

            ResourceType.Error -> flowOf(Result.Error())
        }

    override fun editUserProfile(
        name: String,
        email: String,
        avatar: File?
    ): Flow<Result<JsonObject>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Result.Success())

            ResourceType.Error -> flowOf(Result.Error())
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}