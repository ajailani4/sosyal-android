package com.sosyal.app.ui.screen.profile

sealed class ProfileEvent {
    object GetUserProfile : ProfileEvent()
    data class OnPullRefresh(val isRefreshing: Boolean) : ProfileEvent()
}
