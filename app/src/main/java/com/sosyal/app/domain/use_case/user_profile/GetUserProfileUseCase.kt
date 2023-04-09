package com.sosyal.app.domain.use_case.user_profile

import com.sosyal.app.domain.repository.UserProfileRepository

class GetUserProfileUseCase(
    private val userProfileRepository: UserProfileRepository
) {
    operator fun invoke() = userProfileRepository.getProfile()
}