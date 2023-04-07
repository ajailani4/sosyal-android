package com.sosyal.app.ui.screen.upload_edit_post

sealed class UploadEditPostEvent {
    object UploadPost : UploadEditPostEvent()
    data class OnContentChanged(val content: String) : UploadEditPostEvent()
}
