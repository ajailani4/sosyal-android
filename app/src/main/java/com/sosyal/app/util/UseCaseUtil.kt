package com.sosyal.app.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> CoroutineScope.observeUseCaseResult(
    useCase: Flow<Result<T>>,
    onLoading: (() -> Unit)? = null,
    onSuccess: suspend (data: T?) -> Unit,
    onError: ((String?) -> Unit)? = null
) {
    onLoading?.invoke()

    this.launch {
        useCase.collect { result ->
            when (result) {
                is Result.Success -> onSuccess(result.data)

                is Result.Error -> onError?.invoke(result.message)
            }
        }
    }
}