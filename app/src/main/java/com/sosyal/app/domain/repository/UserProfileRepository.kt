package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(): Flow<Resource<UserProfile>>
}