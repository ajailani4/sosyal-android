package com.sosyal.app.data.repository

import com.sosyal.app.data.local.PreferencesDataStore
import com.sosyal.app.domain.repository.UserCredentialRepository

class UserCredentialRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore
) : UserCredentialRepository {
    override fun getUserCredential() = preferencesDataStore.getUserCredential()

    override suspend fun deleteUserCredential() {
        preferencesDataStore.deleteUserCredential()
    }
}