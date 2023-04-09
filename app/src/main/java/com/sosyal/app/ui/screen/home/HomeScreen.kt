package com.sosyal.app.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sosyal.app.R
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.common.component.BottomSheetItem
import com.sosyal.app.ui.common.component.ProgressBarWithBackground
import com.sosyal.app.ui.screen.home.component.PostItemCard
import com.sosyal.app.ui.theme.backgroundGrey
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    onNavigateToUploadEditPost: (String?) -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val onEvent = homeViewModel::onEvent
    val postsState = homeViewModel.postsState
    val deletePostState = homeViewModel.deletePostState
    val posts = homeViewModel.posts.reversed()
    val username = homeViewModel.username
    val selectedPost = homeViewModel.selectedPost
    val deletePostDialogVis = homeViewModel.deletePostDialogVis

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
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
    ) {
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedButton(
                                modifier = Modifier.size(width = 35.dp, height = 35.dp),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.primary
                                ),
                                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                                contentPadding = PaddingValues(0.dp),
                                onClick = { onNavigateToUploadEditPost(null) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add post icon"
                                )
                            }
                            Spacer(modifier = Modifier.width(15.dp))
                            Image(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .clickable { onNavigateToProfile() },
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = "Profile picture"
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
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
                        items(posts) { post ->
                            PostItemCard(
                                post = post,
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

                    is UIState.Error -> {
                        item {
                            LaunchedEffect(scaffoldState) {
                                postsState.message?.let {
                                    scaffoldState.snackbarHostState.showSnackbar(it)
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }

            // Observe delete post state
            when (deletePostState) {
                UIState.Loading -> ProgressBarWithBackground()

                else -> {}
            }
        }

        // Delete post confirmation dialog
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
}
