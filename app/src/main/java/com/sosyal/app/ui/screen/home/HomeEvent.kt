package com.sosyal.app.ui.screen.home

import com.sosyal.app.domain.model.Post

sealed class HomeEvent {
    data class OnPostSelected(val username: Post) : HomeEvent()
}
