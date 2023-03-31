package com.sosyal.app.ui.screen.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.use_case.auth.RegisterAccountUseCase
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerAccountUseCase: RegisterAccountUseCase
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
                registerState =
                    registerState.copy(passwordVisibility = !registerState.passwordVisibility)
            }
        }
    }

    private fun register() {
        registerState = registerState.copy(registerLoading = true)

        viewModelScope.launch {
            registerState.apply {
                registerAccountUseCase(
                    name = name,
                    email = email,
                    username = username,
                    password = password
                ).catch {
                    registerState = registerState.copy(registerErrorMessage = it.message)
                }.collect {
                    registerState = when (it) {
                        is Resource.Success -> {
                            Log.d("Register status", "Success")
                            registerState.copy(
                                registerLoading = false,
                                registerSuccess = true,
                                registerErrorMessage = null
                            )
                        }

                        is Resource.Error -> {
                            registerState.copy(
                                registerLoading = false,
                                registerErrorMessage = it.message
                            )
                        }
                    }
                }
            }
        }
    }
}