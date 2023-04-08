package com.sosyal.app.ui.screen.upload_edit_post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.post.GetPostDetailUseCase
import com.sosyal.app.domain.use_case.post.UploadPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UploadEditPostViewModel(
    savedStateHandle: SavedStateHandle,
    private val uploadPostUseCase: UploadPostUseCase,
    private val getUserCredentialUseCase: GetUserCredentialUseCase,
    private val getPostDetailUseCase: GetPostDetailUseCase
) : ViewModel() {
    val postId = savedStateHandle.get<String>("postId")

    var uploadPostState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var postDetailState by mutableStateOf<UIState<Post>>(UIState.Idle)
        private set

    var content by mutableStateOf("")
        private set

    init {
        if (postId != null) getPostDetail()
    }

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

    private fun getPostDetail() {
        postDetailState = UIState.Loading

        viewModelScope.launch {
            postId?.let { id ->
                getPostDetailUseCase(id).catch {
                    postDetailState = UIState.Error(it.message)
                }.collect {
                    postDetailState = when (it) {
                        is Resource.Success -> UIState.Success(it.data)

                        is Resource.Error -> UIState.Error(it.message)
                    }
                }
            }
        }
    }
}