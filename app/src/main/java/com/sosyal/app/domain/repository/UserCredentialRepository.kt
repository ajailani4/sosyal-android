package com.sosyal.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserCredentialRepository {
    suspend fun saveAccessToken(accessToken: String)

    fun getAccessToken(): Flow<String>
}