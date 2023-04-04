package com.sosyal.app.data.remote.data_source

import com.sosyal.app.data.remote.api_service.PostService
import com.sosyal.app.data.remote.dto.PostDto

class PostRemoteDataSource(private val postService: PostService) {
    fun getPost() = postService.getPost()

    suspend fun submitPost(postDto: PostDto) {
        postService.submitPost(postDto)
    }
}