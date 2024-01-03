package com.sosyal.app.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.post.DeletePostUseCase
import com.sosyal.app.domain.use_case.post.GetPostsUseCase
import com.sosyal.app.domain.use_case.post.ReceivePostUseCase
import com.sosyal.app.domain.use_case.post.SendPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import com.sosyal.app.util.observeApiResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUserCredentialUseCase: GetUserCredentialUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val receivePostUseCase: ReceivePostUseCase,
    private val sendPostUseCase: SendPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        getUserCredential()
        getUserProfile()
        getPosts()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.RefreshPost -> {
                uiState.posts.clear()
                getPosts()
            }

            HomeEvent.DeletePost -> deletePost()

            HomeEvent.LikeOrDislikePost -> likeOrDislikePost()

            is HomeEvent.OnPostSelected -> uiState = uiState.copy(selectedPost = event.post)

            is HomeEvent.OnPullRefresh -> {
                uiState = uiState.copy(
                    isRefreshing = true,
                    errorMessage = null
                )
            }
        }
    }

    private fun getUserCredential() {
        viewModelScope.launch {
            uiState = uiState.copy(username = getUserCredentialUseCase().first().username)
        }
    }

    private fun getUserProfile() {
        viewModelScope.observeApiResult(
            apiResult = getUserProfileUseCase(),
            onLoading = { uiState = uiState.copy(isUserProfileLoading = true) },
            onSuccess = {
                uiState = uiState.copy(
                    isUserProfileLoading = false,
                    userProfile = it
                )
            },
            onError = { uiState = uiState.copy(isUserProfileLoading = false) }
        )
    }

    private fun getPosts() {
        viewModelScope.observeApiResult(
            apiResult = getPostsUseCase(),
            onLoading = { uiState = uiState.copy(isPostsLoading = true) },
            onSuccess = {
                if (it != null) {
                    uiState = uiState.copy(
                        isRefreshing = false,
                        isPostsLoading = false
                    )
                    uiState.posts.addAll(it)
                }

                receivePost()
            },
            onError = { uiState = uiState.copy(isPostsLoading = false, errorMessage = it) }
        )
    }

    private suspend fun receivePost() {
        receivePostUseCase().collect { post ->
            post.id?.let { postId ->
                val existingPost = uiState.posts.find { it.id == postId }

                if (existingPost == null) {
                    uiState.posts.add(post)
                } else {
                    if (post.isEdited == true) {
                        with(post) {
                            uiState.posts[uiState.posts.indexOf(existingPost)] = existingPost.copy(
                                id = id,
                                username = username,
                                userAvatar = userAvatar,
                                content = content,
                                likes = likes,
                                comments = comments
                            )
                        }
                    }
                }
            }
        }
    }

    private fun likeOrDislikePost() {
        with(uiState.selectedPost) {
            uiState.posts[uiState.posts.indexOf(this)] = this.copy(isLiked = this.isLiked?.not())

            viewModelScope.launch {
                sendPostUseCase(
                    Post(
                        id = id,
                        username = username,
                        content = content,
                        likes = likes,
                        comments = comments,
                        date = date,
                        isEdited = true,
                        isLiked = isLiked?.not()
                    )
                )
            }
        }
    }

    private fun deletePost() {
        uiState.selectedPost.id?.let { id ->
            viewModelScope.observeApiResult(
                apiResult = deletePostUseCase(id),
                onLoading = { uiState = uiState.copy(isPostDeleting = true) },
                onSuccess = {
                    uiState.posts.remove(uiState.selectedPost)

                    uiState = uiState.copy(isPostDeleting = false)
                },
                onError = { uiState = uiState.copy(isPostDeleting = false, errorMessage = it) }
            )
        }
    }
}