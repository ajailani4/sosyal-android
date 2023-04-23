package com.sosyal.app.data.mapper

import com.sosyal.app.data.remote.dto.PostDto
import com.sosyal.app.domain.model.Post

fun PostDto.toPost() =
    Post(
        id = id,
        username = username,
        userAvatar = userAvatar,
        content = content,
        likes = likes,
        comments = comments,
        date = date,
        isEdited = isEdited,
        isLiked = isLiked
    )

fun Post.toPostDto() =
    PostDto(
        id = id,
        username = username,
        content = content,
        likes = likes,
        comments = comments,
        date = date,
        isEdited = isEdited,
        isLiked = isLiked
    )