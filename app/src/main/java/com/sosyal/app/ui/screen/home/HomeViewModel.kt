package com.sosyal.app.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.post.GetPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.ui.common.UIState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val getUserCredentialUseCase: GetUserCredentialUseCase
) : ViewModel() {
    var postsState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var posts = mutableStateListOf<Post>()
        private set

    var username by mutableStateOf("")
        private set

    var selectedPostUsername by mutableStateOf("")
        private set

    init {
        getPosts()
        getUserCredential()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnPostSelected -> selectedPostUsername = event.username
        }
    }

    private fun getPosts() {
        postsState = UIState.Loading

        viewModelScope.launch {
            getPostUseCase().collect { post ->
                postsState = UIState.Success(null)

                postsState.apply {
                    val existedPost = posts.find { it.id == post.id }

                    if (existedPost == null) {
                        posts.add(post)
                    } else {
                        posts[posts.indexOf(existedPost)] = post
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
}