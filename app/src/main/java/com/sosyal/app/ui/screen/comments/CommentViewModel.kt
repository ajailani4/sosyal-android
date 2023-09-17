package com.sosyal.app.ui.screen.comments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Comment
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.comment.ReceiveCommentUseCase
import com.sosyal.app.domain.use_case.comment.SendCommentUseCase
import com.sosyal.app.domain.use_case.post.GetPostDetailUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CommentViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPostDetailUseCase: GetPostDetailUseCase,
    private val getUserCredentialUseCase: GetUserCredentialUseCase,
    private val receiveCommentUseCase: ReceiveCommentUseCase,
    private val sendCommentUseCase: SendCommentUseCase
) : ViewModel() {
    val postId: String? = savedStateHandle["postId"]

    var postDetailState by mutableStateOf<UIState<Post>>(UIState.Idle)
        private set

    var commentsState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var comments = mutableStateListOf<Comment>()
        private set

    var commentContent by mutableStateOf("")
        private set

    init {
        getPostDetail()
        receiveComment()
    }

    fun onEvent(event: CommentEvent) {
        when (event) {
            CommentEvent.UploadComment -> uploadComment()

            is CommentEvent.OnCommentContentChanged -> commentContent = event.content
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

    private fun receiveComment() {
        commentsState = UIState.Loading

        viewModelScope.launch {
            postId?.let { id ->
                receiveCommentUseCase(id).collect { comment ->
                    commentsState = UIState.Success(null)

                    if (comment.postId.isNotEmpty()) {
                        val existedComment = comments.find { it.id == comment.id }

                        if (existedComment == null) {
                            comments.add(comment)
                        } else {
                            comments[comments.indexOf(existedComment)] = comment
                        }
                    }
                }
            }
        }
    }

    private fun uploadComment() {
        viewModelScope.launch {
            val username = getUserCredentialUseCase().first().username

            postId?.let { id ->
                sendCommentUseCase(
                    Comment(
                        postId = id,
                        username = username,
                        content = commentContent
                    )
                )
            }
        }
    }
}