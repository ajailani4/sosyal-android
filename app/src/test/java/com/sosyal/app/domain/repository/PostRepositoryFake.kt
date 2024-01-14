package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import com.sosyal.app.util.ResourceType
import com.sosyal.app.util.ApiResult
import com.sosyal.app.util.post
import com.sosyal.app.util.posts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.JsonObject

class PostRepositoryFake : PostRepository {
    private lateinit var resourceType: ResourceType
    override fun getPosts(): Flow<ApiResult<List<Post>>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(ApiResult.Success(posts))

            ResourceType.Error -> flowOf(ApiResult.Error())
        }

    override fun receivePost(): SharedFlow<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun sendPost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun getPostDetail(id: String): Flow<ApiResult<Post>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(ApiResult.Success(post))

            ResourceType.Error -> flowOf(ApiResult.Error())
        }

    override fun deletePost(id: String): Flow<ApiResult<JsonObject>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(ApiResult.Success())

            ResourceType.Error -> flowOf(ApiResult.Error())
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}