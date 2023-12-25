package com.sosyal.app.util

sealed class Result<T> {
    data class Success<T>(val data: T? = null) : Result<T>()
    data class Error<T>(val message: String? = null) : Result<T>()
}
