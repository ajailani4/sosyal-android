package com.sosyal.app.util

import com.sosyal.app.data.remote.dto.UserCredentialDto
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.model.UserCredential
import com.sosyal.app.domain.model.UserProfile

val userCredential = UserCredential(
    accessToken = "a1b2c3",
    username = "george_z"
)

val userCredentialDto = UserCredentialDto(
    accessToken = "a1b2c3",
    username = "george_z"
)

val userProfile = UserProfile(
    name= "George",
    email = "george@email.com",
    username = "george_z",
    avatar = "avatar_url"
)

val post = Post(
    id = "1",
    username = "george_z",
    content = "Hello",
    likes = 0,
    comments = 0,
    date = "2023-04-19 08:00"
)