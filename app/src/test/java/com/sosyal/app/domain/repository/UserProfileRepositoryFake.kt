package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.JsonObject
import java.io.File

class UserProfileRepositoryFake : UserProfileRepository {
    private lateinit var resourceType: ResourceType

    override fun getUserProfile(): Flow<Resource<UserProfile>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(userProfile))

            ResourceType.Error -> flowOf(Resource.Error())
        }

    override fun editUserProfile(
        name: String,
        email: String,
        avatar: File?
    ): Flow<Resource<JsonObject>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success())

            ResourceType.Error -> flowOf(Resource.Error())
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}