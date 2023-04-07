package com.sosyal.app.ui.screen.upload_edit_post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.post.UploadPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.ui.common.UIState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UploadEditPostViewModel(
    private val uploadPostUseCase: UploadPostUseCase,
    private val getUserCredentialUseCase: GetUserCredentialUseCase
) : ViewModel() {
    var uploadPostState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var content by mutableStateOf("")
        private set

    fun onEvent(event: UploadEditPostEvent) {
        when (event) {
            UploadEditPostEvent.UploadPost -> uploadPost()

            is UploadEditPostEvent.OnContentChanged -> content = event.content
        }
    }

    private fun uploadPost() {
        viewModelScope.launch {
            val userCredential = getUserCredentialUseCase().first()

            uploadPostUseCase(
                username = userCredential.username,
                content = content
            )

            uploadPostState = UIState.Success(null)
        }
    }
}