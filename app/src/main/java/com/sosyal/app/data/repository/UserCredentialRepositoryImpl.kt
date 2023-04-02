package com.sosyal.app.data.repository

import com.sosyal.app.data.local.PreferencesDataStore
import com.sosyal.app.domain.repository.UserCredentialRepository
import kotlinx.coroutines.flow.Flow

class UserCredentialRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore
) : UserCredentialRepository {
    override suspend fun saveAccessToken(accessToken: String) {
        preferencesDataStore.saveAccessToken(accessToken)
    }

    override fun getAccessToken() = preferencesDataStore.getAccessToken()
}