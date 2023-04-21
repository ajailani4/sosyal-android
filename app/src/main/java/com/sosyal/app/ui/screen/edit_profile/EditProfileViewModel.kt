package com.sosyal.app.ui.screen.edit_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.use_case.user_profile.EditUserProfileUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File

class EditProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val editUserProfileUseCase: EditUserProfileUseCase
) : ViewModel() {
    var userProfileState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var editUserProfileState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    /** The avatar will have 2 different types, either String or File.
    If the type is String, it means the avatar is fetched from the server to be displayed on the screen.
    Else if the type is File, it means the avatar is obtained from PickVisualMedia API
    */
    var avatar by mutableStateOf<Any?>(null)
        private set

    var username = ""
        private set

    init {
        getUserProfile()
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            EditProfileEvent.EditProfile -> editUserProfile()

            is EditProfileEvent.OnNameChanged -> name = event.name

            is EditProfileEvent.OnEmailChanged -> email = event.email

            is EditProfileEvent.OnAvatarChanged -> avatar = event.avatar
        }
    }

    private fun getUserProfile() {
        userProfileState = UIState.Loading

        viewModelScope.launch {
            getUserProfileUseCase().catch {
                userProfileState = UIState.Error(it.message)
            }.collect {
                userProfileState = when (it) {
                    is Resource.Success -> {
                        it.data?.let { userProfile ->
                            username = userProfile.username
                            name = userProfile.name
                            email = userProfile.email
                            avatar = userProfile.avatar
                        }

                        UIState.Success(null)
                    }

                    is Resource.Error -> UIState.Error(it.message)
                }
            }
        }
}

    private fun editUserProfile() {
        editUserProfileState = UIState.Loading

        viewModelScope.launch {
            editUserProfileUseCase(
                name = name,
                email = email,
                avatar = if (avatar is File) avatar as File else null
            ).catch {
                editUserProfileState = UIState.Error(it.message)
            }.collect {
                editUserProfileState = when (it) {
                    is Resource.Success -> UIState.Success(null)

                    is Resource.Error -> UIState.Error(it.message)
                }
            }
        }
    }
}