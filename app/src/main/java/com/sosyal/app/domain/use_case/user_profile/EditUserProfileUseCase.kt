package com.sosyal.app.domain.use_case.user_profile

import com.sosyal.app.domain.repository.UserProfileRepository
import java.io.File

class EditUserProfileUseCase(
    private val userProfileRepository: UserProfileRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        avatar: File?
    ) = userProfileRepository.editUserProfile(
        name = name,
        email = email,
        avatar = avatar
    )
}