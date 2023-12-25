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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val uiState = homeViewModel.uiState
    val (showDeletePostDialog, setShowDeletePostDialog) = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = {
            onEvent(HomeEvent.OnPullRefresh)
            onEvent(HomeEvent.RefreshPost)
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HeaderSection(
                uiState = uiState,
                onNavigateToProfile = onNavigateToProfile
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.backgroundGrey)
                    .padding(innerPadding)
            ) {
                when (uiState.isPostsLoading) {
                    true -> {
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

                    false -> {
                        items(uiState.posts.reversed()) { post ->
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

                    else -> Unit
                }
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = uiState.isRefreshing,
                state = pullRefreshState,
                contentColor = MaterialTheme.colors.primary
            )
        }

        if (uiState.isPostDeleting) ProgressBarWithBackground()

        if (showDeletePostDialog) {
            AlertDialog(
                onDismissRequest = { setShowDeletePostDialog(false) },
                title = { Text(text = stringResource(id = R.string.delete_post)) },
                text = { Text(text = stringResource(id = R.string.delete_post_confirm_msg)) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEvent(HomeEvent.DeletePost)
                            setShowDeletePostDialog(false)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.yes))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { setShowDeletePostDialog(false) }
                    ) {
                        Text(text = stringResource(id = R.string.no))
                    }
                }
            )
        }

        uiState.errorMessage?.let {
            LaunchedEffect(scaffoldState) {
                scaffoldState.snackbarHostState.showSnackbar(it)
            }
        }
    }

    onBottomSheetOpened {
        BottomSheetContent(
            username = uiState.username,
            selectedPost = uiState.selectedPost,
            coroutineScope = coroutineScope,
            bottomSheetState = bottomSheetState,
            setShowDeletePostDialog = setShowDeletePostDialog,
            onNavigateToUploadEditPost = onNavigateToUploadEditPost
        )
    }
}

@Composable
private fun HeaderSection(
    uiState: HomeUiState,
    onNavigateToProfile: () -> Unit
) {
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

            with(uiState) {
                when {
                    isUserProfileLoading -> {
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.img_default_ava),
                            contentDescription = "Profile picture"
                        )
                    }

                    userProfile != null -> {
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
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetContent(
    username: String,
    selectedPost: Post,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    setShowDeletePostDialog: (Boolean) -> Unit,
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

                    setShowDeletePostDialog(true)
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