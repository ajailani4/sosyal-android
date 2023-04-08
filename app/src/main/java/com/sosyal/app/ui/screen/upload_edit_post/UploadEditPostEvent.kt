package com.sosyal.app.ui.screen.upload_edit_post

sealed class UploadEditPostEvent {
    object Idle : UploadEditPostEvent()
    object UploadPost : UploadEditPostEvent()
    object EditPost : UploadEditPostEvent()
    data class OnContentChanged(val content: String) : UploadEditPostEvent()
}
