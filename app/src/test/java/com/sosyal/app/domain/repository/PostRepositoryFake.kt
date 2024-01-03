package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import com.sosyal.app.util.ResourceType
import com.sosyal.app.util.Result
import com.sosyal.app.util.post
import com.sosyal.app.util.posts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.JsonObject

class PostRepositoryFake : PostRepository {
    private lateinit var resourceType: ResourceType
    override fun getPosts(): Flow<Result<List<Post>>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Result.Success(posts))

            ResourceType.Error -> flowOf(Result.Error())
        }

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