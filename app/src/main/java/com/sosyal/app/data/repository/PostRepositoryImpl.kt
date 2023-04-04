package com.sosyal.app.data.repository

import com.sosyal.app.data.mapper.toPostDto
import com.sosyal.app.data.remote.data_source.PostRemoteDataSource
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.repository.PostRepository
import kotlinx.coroutines.flow.SharedFlow

class PostRepositoryImpl(
    private val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {
    override fun getPost() = postRemoteDataSource.getPost()

    override suspend fun submitPost(post: Post) {
        postRemoteDataSource.submitPost(post.toPostDto())
    }
}