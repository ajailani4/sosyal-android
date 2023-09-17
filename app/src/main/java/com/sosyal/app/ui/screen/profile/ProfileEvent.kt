package com.sosyal.app.ui.screen.profile

sealed class ProfileEvent {
    object GetUserProfile : ProfileEvent()
    object Logout : ProfileEvent()
    data class OnPullRefresh(val isRefreshing: Boolean) : ProfileEvent()
    data class OnLogoutDialogVisChanged(val isVisible: Boolean) : ProfileEvent()
}
