package com.sosyal.app.data.repository

import android.content.Context
import com.sosyal.app.R
import com.sosyal.app.data.mapper.toPost
import com.sosyal.app.data.mapper.toPostDto
import com.sosyal.app.data.remote.data_source.PostRemoteDataSource
import com.sosyal.app.data.remote.dto.PostDto
import com.sosyal.app.data.remote.dto.response.BaseResponse
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.repository.PostRepository
import com.sosyal.app.util.ApiResult
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.JsonObject

class PostRepositoryImpl(
    private val postRemoteDataSource: PostRemoteDataSource,
    private val context: Context
) : PostRepository {
    override fun getPosts() =
        flow {
            val response = postRemoteDataSource.getPosts()

            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.body<BaseResponse<List<PostDto>>>()
                    emit(ApiResult.Success(responseBody.data?.map { postDto -> postDto.toPost() }))
                }

                HttpStatusCode.InternalServerError -> emit(ApiResult.Error(context.getString(R.string.server_error)))

                else -> emit(ApiResult.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun receivePost() = postRemoteDataSource.receivePost()

    override suspend fun sendPost(post: Post) {
        postRemoteDataSource.sendPost(post.toPostDto())
    }

    override fun getPostDetail(id: String) =
        flow {
            val response = postRemoteDataSource.getPostDetail(id)

            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.body<BaseResponse<PostDto>>()
                    emit(ApiResult.Success(responseBody.data?.toPost()))
                }

                HttpStatusCode.InternalServerError -> emit(ApiResult.Error(context.getString(R.string.server_error)))

                else -> emit(ApiResult.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun deletePost(id: String) =
        flow {
            val response = postRemoteDataSource.deletePost(id)

            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.body<BaseResponse<JsonObject>>()
                    emit(ApiResult.Success(responseBody.data))
                }

                HttpStatusCode.InternalServerError -> emit(ApiResult.Error(context.getString(R.string.server_error)))

                else -> emit(ApiResult.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}