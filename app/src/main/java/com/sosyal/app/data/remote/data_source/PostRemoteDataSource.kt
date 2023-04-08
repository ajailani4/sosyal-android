package com.sosyal.app.data.remote.data_source

import com.sosyal.app.data.remote.api_service.PostService
import com.sosyal.app.data.remote.dto.PostDto

class PostRemoteDataSource(private val postService: PostService) {
    fun receivePost() = postService.receivePost()

    suspend fun sendPost(postDto: PostDto) {
        postService.sendPost(postDto)
    }

    suspend fun getPostDetail(id: String) = postService.getPostDetail(id)
}