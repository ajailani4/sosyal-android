package com.sosyal.app.ui.screen.home

import com.sosyal.app.domain.model.Post

sealed class HomeEvent {
    object DeletePost : HomeEvent()
    data class OnPostSelected(val username: Post) : HomeEvent()
    data class OnDeletePostDialogVisChanged(val isVisible: Boolean) : HomeEvent()
}
