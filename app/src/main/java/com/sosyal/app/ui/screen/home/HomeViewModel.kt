package com.sosyal.app.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.post.DeletePostUseCase
import com.sosyal.app.domain.use_case.post.ReceivePostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val receivePostUseCase: ReceivePostUseCase,
    private val getUserCredentialUseCase: GetUserCredentialUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {
    var postsState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var deletePostState by mutableStateOf<UIState<Nothing>>(UIState.Idle)

    var posts = mutableStateListOf<Post>()
        private set

    var username by mutableStateOf("")
        private set

    var selectedPost by mutableStateOf(Post())
        private set

    var deletePostDialogVis by mutableStateOf(false)
        private set

    init {
        receivePost()
        getUserCredential()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.DeletePost -> deletePost()

            is HomeEvent.OnPostSelected -> selectedPost = event.username

            is HomeEvent.OnDeletePostDialogVisChanged -> deletePostDialogVis = event.isVisible
        }
    }

    private fun receivePost() {
        postsState = UIState.Loading

        viewModelScope.launch {
            receivePostUseCase().collect { post ->
                postsState = UIState.Success(null)

                postsState.apply {
                    val existedPost = posts.find { it.id == post.id }

                    if (existedPost == null) {
                        posts.add(post)
                    } else {
                        if (post.isEdited == true) {
                            posts[posts.indexOf(existedPost)] = post
                        }
                    }
                }
            }
        }
    }

    private fun getUserCredential() {
        viewModelScope.launch {
            username = getUserCredentialUseCase().first().username
        }
    }

    private fun deletePost() {
        deletePostState = UIState.Loading

        viewModelScope.launch {
            selectedPost.id?.let { id ->
                deletePostUseCase(id).catch {
                    deletePostState = UIState.Error(it.message)
                }.collect {
                    deletePostState = when (it) {
                        is Resource.Success -> {
                            posts.remove(selectedPost)
                            UIState.Success(null)
                        }

                        is Resource.Error -> UIState.Error(it.message)
                    }
                }
            }
        }
    }
}