package com.sosyal.app.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sosyal.app.R
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.common.component.BottomSheetItem
import com.sosyal.app.ui.common.component.ProgressBarWithBackground
import com.sosyal.app.ui.screen.home.component.PostItemCard
import com.sosyal.app.ui.theme.backgroundGrey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    bottomSheetState: ModalBottomSheetState,
    onBottomSheetOpened: (content: @Composable ColumnScope.() -> Unit) -> Unit,
    onNavigateToComment: (String?) -> Unit,
    onNavigateToUploadEditPost: (String?) -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val onEvent = homeViewModel::onEvent
    val postsState = homeViewModel.postsState
    val deletePostState = homeViewModel.deletePostState
    val userProfileState = homeViewModel.userProfileState
    val posts = homeViewModel.posts.reversed()
    val username = homeViewModel.username
    val selectedPost = homeViewModel.selectedPost
    val deletePostDialogVis = homeViewModel.deletePostDialogVis
    val pullRefreshing = homeViewModel.pullRefreshing

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = pullRefreshing,
        onRefresh = {
            onEvent(HomeEvent.OnPullRefresh(true))
            onEvent(HomeEvent.RefreshPost)
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Surface(elevation = 4.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.surface)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(width = 100.dp, height = 40.dp),
                        painter = painterResource(id = R.drawable.sosyal_text_logo),
                        contentDescription = "Sosyal text logo"
                    )
                    UserAvatar(
                        userProfileState = userProfileState,
                        onNavigateToProfile = onNavigateToProfile
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.backgroundGrey)
                    .padding(innerPadding)
            ) {
                when (postsState) {
                    UIState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 180.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is UIState.Success -> {
                        onEvent(HomeEvent.OnPullRefresh(false))

                        items(posts) { post ->
                            PostItemCard(
                                post = post,
                                onCardClicked = { onNavigateToComment(post.id) },
                                onLikeClicked = {
                                    onEvent(HomeEvent.OnPostSelected(post))
                                    onEvent(HomeEvent.LikeOrDislikePost)
                                },
                                onCommentClicked = {},
                                onMoreClicked = {
                                    onEvent(HomeEvent.OnPostSelected(post))

                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    else -> {}
                }
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = pullRefreshing,
                state = pullRefreshState,
                contentColor = MaterialTheme.colors.primary
            )
        }

        when (deletePostState) {
            UIState.Loading -> ProgressBarWithBackground()

            else -> {}
        }

        if (deletePostDialogVis) {
            AlertDialog(
                onDismissRequest = {
                    onEvent(HomeEvent.OnDeletePostDialogVisChanged(false))
                },
                title = { Text(text = stringResource(id = R.string.delete_post)) },
                text = { Text(text = stringResource(id = R.string.delete_post_confirm_msg)) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEvent(HomeEvent.DeletePost)
                            onEvent(HomeEvent.OnDeletePostDialogVisChanged(false))
                        }
                    ) {
                        Text(text = stringResource(id = R.string.yes))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onEvent(HomeEvent.OnDeletePostDialogVisChanged(false))
                        }
                    ) {
                        Text(text = stringResource(id = R.string.no))
                    }
                }
            )
        }
    }

    onBottomSheetOpened {
        BottomSheetContent(
            onEvent = onEvent,
            username = username,
            selectedPost = selectedPost,
            coroutineScope = coroutineScope,
            bottomSheetState = bottomSheetState,
            onNavigateToUploadEditPost = onNavigateToUploadEditPost
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetContent(
    onEvent: (HomeEvent) -> Unit,
    username: String,
    selectedPost: Post,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    onNavigateToUploadEditPost: (String?) -> Unit
) {
    if (username == selectedPost.username) {
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            BottomSheetItem(
                icon = Icons.Default.Edit,
                title = stringResource(id = R.string.edit),
                onClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }

                    onNavigateToUploadEditPost(selectedPost.id)
                }
            )
            BottomSheetItem(
                icon = Icons.Default.Delete,
                title = stringResource(id = R.string.delete),
                onClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }

                    onEvent(HomeEvent.OnDeletePostDialogVisChanged(true))
                }
            )
        }
    } else {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(id = R.string.no_menus),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
private fun UserAvatar(
    userProfileState: UIState<UserProfile>,
    onNavigateToProfile: () -> Unit
) {
    when (userProfileState) {
        UIState.Loading -> {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.img_default_ava),
                contentDescription = "Profile picture"
            )
        }

        is UIState.Success -> {
            userProfileState.data?.let { userProfile ->
                AsyncImage(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onNavigateToProfile() },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userProfile.avatar ?: R.drawable.img_default_ava)
                        .placeholder(R.drawable.img_default_ava)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "User profile avatar"
                )
            }
        }

        else -> {}
    }
}
