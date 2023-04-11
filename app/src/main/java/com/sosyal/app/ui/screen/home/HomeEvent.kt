package com.sosyal.app.ui.screen.home

import com.sosyal.app.domain.model.Post

sealed class HomeEvent {
    object DeletePost : HomeEvent()
    object LikeOrDislikePost : HomeEvent()
    data class OnPostSelected(val post: Post) : HomeEvent()
    data class OnDeletePostDialogVisChanged(val isVisible: Boolean) : HomeEvent()
}
