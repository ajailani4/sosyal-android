package com.sosyal.app.data.remote.dto.response

data class BaseResponse<T>(
    val message: String,
    val data: T? = null
)
