package com.sosyal.app.ui.screen.login

import com.sosyal.app.domain.model.UserCredential
import com.sosyal.app.ui.common.UIState

data class LoginState(
    val username: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val uiState: UIState<UserCredential> = UIState.Idle
)
