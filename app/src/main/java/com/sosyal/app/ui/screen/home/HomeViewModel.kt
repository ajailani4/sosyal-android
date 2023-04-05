package com.sosyal.app.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.post.GetPostUseCase
import com.sosyal.app.ui.common.UIState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPostUseCase: GetPostUseCase
) : ViewModel() {
    var homeState by mutableStateOf(HomeState())
        private set

    var posts = mutableStateListOf<Post>()
        private set

    init {
        getPosts()
    }

    private fun getPosts() {
        homeState = homeState.copy(uiState = UIState.Loading)

        viewModelScope.launch {
            getPostUseCase().collect { post ->
                homeState = homeState.copy(uiState = UIState.Success(null))

                homeState.apply {
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
}