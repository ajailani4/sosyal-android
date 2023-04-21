package com.sosyal.app.domain.repository

import com.sosyal.app.domain.model.Post
import com.sosyal.app.util.Resource
import com.sosyal.app.util.ResourceType
import com.sosyal.app.util.post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.JsonObject

class PostRepositoryFake : PostRepository {
    private lateinit var resourceType: ResourceType
    private lateinit var coroutineScope: CoroutineScope

    override fun receivePost(): SharedFlow<Post> =
        flowOf(post).shareIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly
        )

    override suspend fun sendPost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun getPostDetail(id: String): Flow<Resource<Post>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(post))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun deletePost(id: String): Flow<Resource<JsonObject>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(null))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }

    fun setCoroutineScope(scope: CoroutineScope) {
        coroutineScope = scope
    }
}