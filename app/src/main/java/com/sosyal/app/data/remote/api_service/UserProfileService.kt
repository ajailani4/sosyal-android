package com.sosyal.app.data.remote.api_service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.io.File

class UserProfileService(private val httpClient: HttpClient) {
    suspend fun getUserProfile() = httpClient.get("/profile")
    suspend fun editUserProfile(
        name: String,
        email: String,
        avatar: File?
    ) =
        httpClient.submitFormWithBinaryData(
            url = "/profile",
            formData = formData {
                append("name", name)
                append("email", email)

                avatar?.readBytes()?.let {
                    append("avatar", it, Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(HttpHeaders.ContentDisposition, "filename=\"${avatar.name}\"")
                    })
                }
            }
        )
}