package com.sosyal.app.ui.screen.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.use_case.auth.RegisterAccountUseCase
import com.sosyal.app.domain.use_case.user_credential.SaveAccessTokenUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) : ViewModel() {
    var registerState by mutableStateOf(RegisterState())

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.Register -> register()

            is RegisterEvent.OnNameChanged -> {
                registerState = registerState.copy(name = event.name)
            }

            is RegisterEvent.OnEmailChanged -> {
                registerState = registerState.copy(email = event.email)
            }

            is RegisterEvent.OnUsernameChanged -> {
                registerState = registerState.copy(username = event.username)
            }

            is RegisterEvent.OnPasswordChanged -> {
                registerState = registerState.copy(password = event.password)
            }

            RegisterEvent.OnPasswordVisibilityChanged -> {
                registerState = registerState.copy(passwordVisibility = !registerState.passwordVisibility)
            }
        }
    }

    private fun register() {
        registerState = registerState.copy(uiState = UIState.Loading)

        viewModelScope.launch {
            registerState.apply {
                registerAccountUseCase(
                    name = name,
                    email = email,
                    username = username,
                    password = password
                ).catch {
                    registerState = registerState.copy(uiState = UIState.Error(it.message))
                }.collect {
                    registerState = when (it) {
                        is Resource.Success -> {
                            it.data?.accessToken?.let { accessToken ->
                                saveAccessTokenUseCase(accessToken)
                            }

                            registerState.copy(uiState = UIState.Success(null))
                        }

                        is Resource.Error -> {
                            registerState.copy(uiState = UIState.Error(it.message))
                        }
                    }
                }
            }
        }
    }
}