package com.sosyal.app.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.use_case.auth.LoginAccountUseCase
import com.sosyal.app.domain.use_case.user_credential.SaveAccessTokenUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginAccountUseCase: LoginAccountUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) : ViewModel() {
    var loginState by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.Login -> login()

            is LoginEvent.OnUsernameChanged -> {
                loginState = loginState.copy(username = event.username)
            }

            is LoginEvent.OnPasswordChanged -> {
                loginState = loginState.copy(password = event.password)
            }

            LoginEvent.OnPasswordVisibilityChanged -> {
                loginState = loginState.copy(passwordVisibility = !loginState.passwordVisibility)
            }
        }
    }

    private fun login() {
        loginState = loginState.copy(uiState = UIState.Loading)

        viewModelScope.launch {
            loginAccountUseCase(
                username = loginState.username,
                password = loginState.password
            ).catch {
                loginState = loginState.copy(uiState = UIState.Error(it.message))
            }.collect {
                loginState = when (it) {
                    is Resource.Success -> {
                        it.data?.accessToken?.let {  accessToken ->
                            saveAccessTokenUseCase(accessToken)
                        }

                        loginState.copy(uiState = UIState.Success(null))
                    }

                    is Resource.Error -> {
                        loginState.copy(uiState = UIState.Error(it.message))
                    }
                }
            }
        }
    }
}