package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.UserCredential
import com.sosyal.app.util.ApiResult
import com.sosyal.app.util.ResourceType
import com.sosyal.app.util.userCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryFake : AuthRepository {
    private lateinit var resourceType: ResourceType

    override fun register(
        name: String,
        email: String,
        username: String,
        password: String
    ): Flow<ApiResult<UserCredential>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(ApiResult.Success(userCredential))

            ResourceType.Error -> flowOf(ApiResult.Error())
        }

    override fun login(
        username: String,
        password: String
    ): Flow<ApiResult<UserCredential>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(ApiResult.Success(userCredential))

            ResourceType.Error -> flowOf(ApiResult.Error())
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}