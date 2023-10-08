package com.sosyal.app.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.domain.use_case.post.DeletePostUseCase
import com.sosyal.app.domain.use_case.post.GetPostsUseCase
import com.sosyal.app.domain.use_case.post.ReceivePostUseCase
import com.sosyal.app.domain.use_case.post.SendPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.Resource
import kotlinx.coroutines.flow.catch
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
    var postsState by mutableStateOf<UIState<List<Nothing>>>(UIState.Idle)
        private set

    var deletePostState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var userProfileState by mutableStateOf<UIState<UserProfile>>(UIState.Idle)
        private set

    val posts = mutableStateListOf<Post>()

    var username by mutableStateOf("")
        private set

    var selectedPost by mutableStateOf(
        Post(
            username = "",
            content = "",
            likes = 0,
            comments = 0,
            date = ""
        )
    )
        private set

    var deletePostDialogVis by mutableStateOf(false)
        private set

    var pullRefreshing by mutableStateOf(false)
        private set

    init {
        getUserCredential()
        getUserProfile()
        getPosts()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.RefreshPost -> {
                posts.clear()
                getPosts()
            }

            HomeEvent.DeletePost -> deletePost()

            HomeEvent.LikeOrDislikePost -> likeOrDislikePost()

            is HomeEvent.OnPostSelected -> selectedPost = event.post

            is HomeEvent.OnDeletePostDialogVisChanged -> deletePostDialogVis = event.isVisible

            is HomeEvent.OnPullRefresh -> pullRefreshing = event.isRefreshing
        }
    }

    private fun getUserCredential() {
        viewModelScope.launch {
            username = getUserCredentialUseCase().first().username
        }
    }

    private fun getUserProfile() {
        userProfileState = UIState.Loading

        viewModelScope.launch {
            getUserProfileUseCase().catch {
                userProfileState = UIState.Error(it.message)
            }.collect {
                userProfileState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Error(it.message)
                }
            }
        }
    }

    private fun getPosts() {
        postsState = UIState.Loading

        viewModelScope.launch {
            getPostsUseCase().catch {
                postsState = UIState.Error(it.message)
            }.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data != null) {
                            posts.addAll(it.data)
                        }

                        postsState = UIState.Success(null)

                        receivePost()
                    }

                    is Resource.Error -> postsState = UIState.Error(it.message)
                }
            }
        }
    }

    private suspend fun receivePost() {
        receivePostUseCase().collect { post ->
            post.id?.let { postId ->
                val existedPost = posts.find { it.id == postId }

                if (existedPost == null) {
                    posts.add(post)
                } else {
                    if (post.isEdited == true) {
                        with(post) {
                            posts[posts.indexOf(existedPost)] = existedPost.copy(
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
        posts[posts.indexOf(selectedPost)] = selectedPost.copy(isLiked = selectedPost.isLiked?.not())

        viewModelScope.launch {
            with(selectedPost) {
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