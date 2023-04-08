package com.sosyal.app.data.repository

import com.sosyal.app.data.mapper.toPostDto
import com.sosyal.app.data.remote.data_source.PostRemoteDataSource
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.repository.PostRepository
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(
    private val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {
    override fun receivePost() = postRemoteDataSource.receivePost()

    override suspend fun uploadPost(post: Post) {
        postRemoteDataSource.uploadPost(post.toPostDto())
    }

    override fun getPostDetail(): Flow<Resource<Post>> {
        TODO("Not yet implemented")
    }
}