package com.sosyal.app.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> CoroutineScope.observeApiResult(
    apiResult: Flow<Result<T>>?,
    onLoading: (() -> Unit)? = null,
    onSuccess: suspend (data: T?) -> Unit,
    onError: ((String?) -> Unit)? = null
) {
    onLoading?.invoke()

    this.launch {
        apiResult?.collect { result ->
            when (result) {
                is Result.Success -> onSuccess(result.data)

                is Result.Error -> onError?.invoke(result.message)
            }
        }
    }
}