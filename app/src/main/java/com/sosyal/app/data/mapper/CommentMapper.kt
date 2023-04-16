package com.sosyal.app.data.mapper

import com.sosyal.app.data.remote.dto.CommentDto
import com.sosyal.app.domain.model.Comment

fun CommentDto.toComment() =
    Comment(
        id = id,
        postId = postId,
        username = username,
        content = content
    )

fun Comment.toCommentDto() =
    CommentDto(
        id = id,
        postId = postId,
        username = username,
        content = content
    )