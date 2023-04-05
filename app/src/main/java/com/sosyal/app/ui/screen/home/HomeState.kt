package com.sosyal.app.ui.screen.home

import com.sosyal.app.domain.model.Post
import com.sosyal.app.ui.common.UIState

data class HomeState(
    val uiState: UIState<Nothing> = UIState.Idle
)
