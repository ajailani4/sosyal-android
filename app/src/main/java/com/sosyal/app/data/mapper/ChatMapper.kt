package com.sosyal.app.data.mapper

import com.sosyal.app.data.remote.dto.ChatDto
import com.sosyal.app.domain.model.Chat

fun ChatDto.toChat() =
    Chat(
        id = id,
        participants = participants
    )