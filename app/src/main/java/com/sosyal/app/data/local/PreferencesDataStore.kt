package com.sosyal.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sosyal.app.domain.model.UserCredential
import com.sosyal.app.util.Constants
import kotlinx.coroutines.flow.map

class PreferencesDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            Constants.DataStore.PREFERENCES_NAME
        )
        private val ACCESS_TOKEN = stringPreferencesKey(Constants.DataStore.ACCESS_TOKEN_KEY)
        private val USERNAME = stringPreferencesKey(Constants.DataStore.USERNAME_KEY)
    }

    suspend fun saveUserCredential(userCredential: UserCredential) {
        context.dataStore.edit {
            userCredential.apply {
                it[ACCESS_TOKEN] = accessToken
                it[USERNAME] = username
            }
        }
    }

    fun getUserCredential() =
        context.dataStore.data.map {
            UserCredential(
                accessToken = it[ACCESS_TOKEN] ?: "",
                username = it[USERNAME] ?: ""
            )
        }
}