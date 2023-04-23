package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import com.sosyal.app.util.Resource
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

    override fun getPostDetail(id: String): Flow<Resource<Post>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(post))

            ResourceType.Error -> flowOf(Resource.Error())
        }

    override fun deletePost(id: String): Flow<Resource<JsonObject>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success())

            ResourceType.Error -> flowOf(Resource.Error())
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}