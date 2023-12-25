package com.sosyal.app.ui.screen.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.model.UserProfile

data class HomeUiState(
    val isRefreshing: Boolean = false,
    val isUserProfileLoading: Boolean = false,
    val isPostsLoading: Boolean? = null,
    val isPostDeleting: Boolean = false,
    val posts: SnapshotStateList<Post> = mutableStateListOf(),
    val userProfile: UserProfile? = null,
    val username: String = "",
    val selectedPost: Post = Post(
        username = "",
        content = "",
        likes = 0,
        comments = 0,
        date = ""
    ),
    val errorMessage: String? = null
)