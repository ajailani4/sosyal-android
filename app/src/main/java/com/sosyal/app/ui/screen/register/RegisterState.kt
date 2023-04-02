package com.sosyal.app.ui.screen.register

import com.sosyal.app.domain.model.UserCredential
import com.sosyal.app.ui.common.UIState

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val uiState: UIState<UserCredential> = UIState.Idle
)
