package com.sosyal.app.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> CoroutineScope.collectApiResult(
    apiResult: Flow<ApiResult<T>>?,
    onLoading: (() -> Unit)? = null,
    onSuccess: suspend (data: T?) -> Unit,
    onError: ((String?) -> Unit)? = null
) {
    onLoading?.invoke()

    this.launch {
        apiResult?.collect { result ->
            when (result) {
                is ApiResult.Success -> onSuccess(result.data)

                is ApiResult.Error -> onError?.invoke(result.message)
            }
        }
    }
}