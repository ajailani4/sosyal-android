package com.sosyal.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sosyal.app.util.Constants
import kotlinx.coroutines.flow.map

class PreferencesDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            Constants.DataStore.PREFERENCES_NAME
        )
        private val ACCESS_TOKEN = stringPreferencesKey(Constants.DataStore.ACCESS_TOKEN_KEY)
    }

    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    fun getAccessToken() =
        context.dataStore.data.map { it[ACCESS_TOKEN] ?: "" }
}