package com.sosyal.app.data.repository

import com.sosyal.app.domain.repository.UserCredentialRepository
import kotlinx.coroutines.flow.Flow

class UserCredentialRepositoryImpl : UserCredentialRepository {
    override suspend fun saveAccessToken(accessToken: String) {
        TODO("Not yet implemented")
    }

    override fun getAccessToken(): Flow<String> {
        TODO("Not yet implemented")
    }
}