package com.sosyal.app.ui.screen.register

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val registerLoading: Boolean = false,
    val registerSuccess: Boolean = false,
    val registerErrorMessage: String? = null
)
