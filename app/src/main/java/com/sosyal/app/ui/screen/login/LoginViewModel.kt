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
    var loginState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordVisibility by mutableStateOf(false)
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.Login -> login()

            is LoginEvent.OnUsernameChanged -> username = event.username

            is LoginEvent.OnPasswordChanged -> password = event.password

            LoginEvent.OnPasswordVisibilityChanged -> passwordVisibility = !passwordVisibility
        }
    }

    private fun login() {
        loginState = UIState.Loading

        viewModelScope.launch {
            loginAccountUseCase(
                username = username,
                password = password
            ).catch {
                loginState = UIState.Error(it.message)
            }.collect {
                loginState = when (it) {
                    is Resource.Success -> {
                        it.data?.accessToken?.let { accessToken ->
                            saveAccessTokenUseCase(accessToken)
                        }

                        UIState.Success(null)
                    }

                    is Resource.Error -> UIState.Error(it.message)
                }
            }
        }
    }
}