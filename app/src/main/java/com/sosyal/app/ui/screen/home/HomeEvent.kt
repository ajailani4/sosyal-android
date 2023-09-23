package com.sosyal.app.ui.screen.home

import com.sosyal.app.domain.model.Post

sealed class HomeEvent {
    object RefreshPost : HomeEvent()
    object DeletePost : HomeEvent()
    object LikeOrDislikePost : HomeEvent()
    data class OnPostSelected(val post: Post) : HomeEvent()
    data class OnDeletePostDialogVisChanged(val isVisible: Boolean) : HomeEvent()
    data class OnPullRefresh(val isRefreshing: Boolean) : HomeEvent()
}
