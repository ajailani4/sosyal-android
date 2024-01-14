package com.sosyal.app.data.repository

import android.content.Context
import com.sosyal.app.R
import com.sosyal.app.data.local.PreferencesDataStore
import com.sosyal.app.data.mapper.toUserCredential
import com.sosyal.app.data.remote.data_source.AuthRemoteDataSource
import com.sosyal.app.data.remote.dto.UserCredentialDto
import com.sosyal.app.data.remote.dto.request.LoginRequest
import com.sosyal.app.data.remote.dto.request.RegisterRequest
import com.sosyal.app.data.remote.dto.response.BaseResponse
import com.sosyal.app.domain.repository.AuthRepository
import com.sosyal.app.util.ApiResult
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    private val context: Context
) : AuthRepository {
    override fun register(
        name: String,
        email: String,
        username: String,
        password: String
    ) =
        flow {
            val response = authRemoteDataSource.register(
                RegisterRequest(
                    name = name,
                    email = email,
                    username = username,
                    password = password
                )
            )

            when (response.status) {
                HttpStatusCode.Created -> {
                    val responseBody = response.body<BaseResponse<UserCredentialDto>>()
                    responseBody.data?.let {
                        preferencesDataStore.saveUserCredential(it.toUserCredential())
                    }

                    emit(ApiResult.Success(responseBody.data?.toUserCredential()))
                }

                HttpStatusCode.Conflict -> emit(ApiResult.Error(context.getString(R.string.username_already_exists)))

                HttpStatusCode.InternalServerError -> emit(ApiResult.Error(context.getString(R.string.server_error)))

                else -> emit(ApiResult.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun login(
        username: String,
        password: String
    ) =
        flow {
            val response = authRemoteDataSource.login(
                LoginRequest(
                    username = username,
                    password = password
                )
            )

            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.body<BaseResponse<UserCredentialDto>>()
                    responseBody.data?.let {
                        preferencesDataStore.saveUserCredential(it.toUserCredential())
                    }

                    emit(ApiResult.Success(responseBody.data?.toUserCredential()))
                }

                HttpStatusCode.Unauthorized -> emit(ApiResult.Error(context.getString(R.string.incorrect_username_or_pass)))

                HttpStatusCode.InternalServerError -> emit(ApiResult.Error(context.getString(R.string.server_error)))

                else -> emit(ApiResult.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}