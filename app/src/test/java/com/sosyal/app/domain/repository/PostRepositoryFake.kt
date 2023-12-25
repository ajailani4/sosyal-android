package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import com.sosyal.app.util.Result
import com.sosyal.app.util.ResourceType
import com.sosyal.app.util.post
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.JsonObject

class PostRepositoryFake : PostRepository {
    private lateinit var resourceType: ResourceType

    override fun receivePost(): SharedFlow<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun sendPost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun getPostDetail(id: String): Flow<Result<Post>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Result.Success(post))

            ResourceType.Error -> flowOf(Result.Error())
        }

    override fun deletePost(id: String): Flow<Result<JsonObject>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Result.Success())

            ResourceType.Error -> flowOf(Result.Error())
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}