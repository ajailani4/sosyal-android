package com.sosyal.app.ui.screen.upload_edit_post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.post.GetPostDetailUseCase
import com.sosyal.app.domain.use_case.post.SendPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.Formatter
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date

class UploadEditPostViewModel(
    savedStateHandle: SavedStateHandle,
    private val sendPostUseCase: SendPostUseCase,
    private val getUserCredentialUseCase: GetUserCredentialUseCase,
    private val getPostDetailUseCase: GetPostDetailUseCase
) : ViewModel() {
    val postId: String? = savedStateHandle["postId"]

    var uploadPostState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var editPostState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var postDetailState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var content by mutableStateOf("")
        private set

    private var post = Post(
        username = "",
        content = "",
        likes = 0,
        comments = 0,
        date = ""
    )

    init {
        if (postId != null) getPostDetail()
    }

    fun onEvent(event: UploadEditPostEvent) {
        when (event) {
            UploadEditPostEvent.Idle -> idle()

            UploadEditPostEvent.UploadPost -> uploadPost()

            UploadEditPostEvent.EditPost -> editPost()

            is UploadEditPostEvent.OnContentChanged -> content = event.content
        }
    }

    private fun idle() {
        postDetailState = UIState.Idle
    }

    private fun getPostDetail() {
        postDetailState = UIState.Loading

        viewModelScope.launch {
            postId?.let { id ->
                getPostDetailUseCase(id).catch {
                    postDetailState = UIState.Error(it.message)
                }.collect { resource ->
                    postDetailState = when (resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                post = it
                                content = post.content
                            }

                            UIState.Success(null)
                        }

                        is Resource.Error -> UIState.Error(resource.message)
                    }
                }
            }
        }
    }

    private fun uploadPost() {
        viewModelScope.launch {
            val username = getUserCredentialUseCase().first().username

            sendPostUseCase(
                Post(
                    username = username,
                    content = content,
                    likes = 0,
                    comments = 0,
                    date = Formatter.convertDateToString(Date())
                )
            )

            uploadPostState = UIState.Success(null)
        }
    }

    private fun editPost() {
        viewModelScope.launch {
            postId?.let { id ->
                sendPostUseCase(
                    Post(
                        id = id,
                        username = post.username,
                        content = content,
                        likes = post.likes,
                        comments = post.comments,
                        date = post.date,
                        isEdited = true,
                        isLiked = post.isLiked
                    )
                )

                editPostState = UIState.Success(null)
            }
        }
    }
}