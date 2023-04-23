package com.sosyal.app.ui.common

sealed class UIState<out T> {
    object Idle : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T?) : UIState<T>()
    data class Error(val message: String?) : UIState<Nothing>()
}
