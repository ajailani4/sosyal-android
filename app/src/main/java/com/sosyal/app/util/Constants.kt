package com.sosyal.app.util

import java.time.format.DateTimeFormatter

class Constants {
    object DataStore {
        const val PREFERENCES_NAME = "sosyalPreferences"
        const val ACCESS_TOKEN_KEY = "accessToken"
    }

    object CoroutineDispatcher {
        const val IO_DISPATCHER = "IODispatcher"
    }

    object BaseUrl {
        const val HTTPS = "HTTPS"
        const val WS = "WS"
    }
}