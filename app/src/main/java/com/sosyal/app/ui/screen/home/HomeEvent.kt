package com.sosyal.app.ui.screen.home

sealed class HomeEvent {
    data class OnPostSelected(val username: String) : HomeEvent()
}
