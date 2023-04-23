package com.sosyal.app.data.repository

import android.content.Context
import com.sosyal.app.R
import com.sosyal.app.data.mapper.toUserProfile
import com.sosyal.app.data.remote.data_source.UserProfileRemoteDataSource
import com.sosyal.app.data.remote.dto.UserProfileDto
import com.sosyal.app.data.remote.dto.response.BaseResponse
import com.sosyal.app.domain.repository.UserProfileRepository
import com.sosyal.app.util.Resource
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.JsonObject
import java.io.File

class UserProfileRepositoryImpl(
    private val userProfileRemoteDataSource: UserProfileRemoteDataSource,
    private val context: Context
) : UserProfileRepository {
    override fun getUserProfile() =
        flow {
            val response = userProfileRemoteDataSource.getUserProfile()

            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.body() as BaseResponse<UserProfileDto>
                    emit(Resource.Success(responseBody.data?.toUserProfile()))
                }

                HttpStatusCode.InternalServerError -> emit(Resource.Error(context.getString(R.string.server_error)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun editUserProfile(
        name: String,
        email: String,
        avatar: File?
    ) =
        flow {
            val response = userProfileRemoteDataSource.editUserProfile(
                name = name,
                email = email,
                avatar = avatar
            )

            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.body() as BaseResponse<JsonObject>
                    emit(Resource.Success(responseBody.data))
                }

                HttpStatusCode.InternalServerError -> emit(Resource.Error(context.getString(R.string.server_error)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}