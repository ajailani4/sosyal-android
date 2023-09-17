package com.sosyal.app.ui.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.domain.use_case.user_credential.DeleteUserCredentialUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val deleteUserCredentialUseCase: DeleteUserCredentialUseCase
) : ViewModel() {
    var userProfileState by mutableStateOf<UIState<UserProfile>>(UIState.Idle)
        private set

    var logoutState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var pullRefreshing by mutableStateOf(false)
        private set

    var logoutDialogVisibility by mutableStateOf(false)
        private set

    init {
        getUserProfile()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.GetUserProfile -> getUserProfile()

            ProfileEvent.Logout -> logout()

            is ProfileEvent.OnPullRefresh -> pullRefreshing = event.isRefreshing

            is ProfileEvent.OnLogoutDialogVisChanged -> logoutDialogVisibility = event.isVisible
        }
    }


    private fun getUserProfile() {
        userProfileState = UIState.Loading

        viewModelScope.launch {
            getUserProfileUseCase().catch {
                userProfileState = UIState.Error(it.message)
            }.collect {
                userProfileState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Error(it.message)
                }
            }
        }
    }

    private fun logout() {
        logoutState = UIState.Loading

        viewModelScope.launch {
            deleteUserCredentialUseCase()
            logoutState = UIState.Success(null)
        }
    }
}
