package com.sosyal.app.ui.screen.edit_profile

sealed class EditProfileEvent {
    object EditProfile : EditProfileEvent()
    data class OnNameChanged(val name: String) : EditProfileEvent()
    data class OnEmailChanged(val email: String) : EditProfileEvent()
    data class OnAvatarChanged(val avatar: Any) : EditProfileEvent()
}
