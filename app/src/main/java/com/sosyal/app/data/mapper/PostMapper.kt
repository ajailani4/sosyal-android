package com.sosyal.app.data.mapper

import com.sosyal.app.data.remote.dto.PostDto
import com.sosyal.app.domain.model.Post

fun PostDto.toPost() =
    Post(
        id = id!!,
        username = username,
        userAvatar = userAvatar,
        content = content,
        likes = likes,
        comments = comments,
        date = date
    )

fun Post.toPostDto() =
    PostDto(
        username = username,
        userAvatar = userAvatar,
        content = content,
        likes = likes,
        comments = comments,
        date = date
    )