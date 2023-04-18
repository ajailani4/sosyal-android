package com.sosyal.app.ui.screen.comments

sealed class CommentEvent {
    object UploadComment : CommentEvent()
    data class OnCommentContentChanged(val content: String) : CommentEvent()
}
