package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.UserCredential
import kotlinx.coroutines.flow.Flow

interface UserCredentialRepository {
    fun getUserCredential(): Flow<UserCredential>

    suspend fun deleteUserCredential()
}