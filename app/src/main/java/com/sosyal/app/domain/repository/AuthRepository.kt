package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.UserCredential
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun register(
        name: String,
        email: String,
        username: String,
        password: String
    ): Flow<Resource<UserCredential>>
}