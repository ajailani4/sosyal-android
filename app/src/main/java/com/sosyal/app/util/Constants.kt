package com.sosyal.app.util

class Constants {
    object DataStore {
        const val PREFERENCES_NAME = "sosyalPreferences"
        const val ACCESS_TOKEN_KEY = "accessToken"
        const val USERNAME_KEY = "username"
    }

    object CoroutineDispatcher {
        const val IO_DISPATCHER = "IODispatcher"
    }

    object BaseUrl {
        const val HTTPS = "HTTPS"
        const val WS = "WS"
    }
}