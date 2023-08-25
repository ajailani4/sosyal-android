package com.sosyal.app.ui.screen.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sosyal.app.R
import com.sosyal.app.domain.model.Comment
import com.sosyal.app.domain.model.DateTimeInfo
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.common.component.formattedDateTime
import com.sosyal.app.ui.screen.comments.component.CommentItemCard
import com.sosyal.app.ui.theme.Grey
import com.sosyal.app.ui.theme.Grey3
import com.sosyal.app.util.Formatter
import org.koin.androidx.compose.koinViewModel

@Composable
fun CommentsScreen(
    commentViewModel: CommentViewModel = koinViewModel(),
    onNavigateUp: () -> Unit
) {
    val onEvent = commentViewModel::onEvent
    val postDetailState = commentViewModel.postDetailState
    val commentsState = commentViewModel.commentsState
    val comments = commentViewModel.comments.reversed()
    val commentContent = commentViewModel.commentContent
    var isCommentFocused by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back icon"
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.comments_title),
                        style = MaterialTheme.typography.h2
                    )
                },
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (postDetailState) {
                    UIState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is UIState.Success -> {
                        postDetailState.data?.let { post ->
                            item {
                                Card(
                                    shape = RectangleShape,
                                    elevation = 0.dp
                                ) {
                                    Column(modifier = Modifier.padding(20.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            AsyncImage(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(CircleShape),
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(
                                                        post.userAvatar
                                                            ?: R.drawable.img_default_ava
                                                    )
                                                    .placeholder(R.drawable.img_default_ava)
                                                    .build(),
                                                contentDescription = "User profile picture"
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(
                                                    text = post.username,
                                                    style = MaterialTheme.typography.subtitle1
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = formattedDateTime(
                                                        Formatter.convertStringToDateOrTime(post.date)
                                                    ),
                                                    style = MaterialTheme.typography.caption.copy(
                                                        color = Grey
                                                    )
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(15.dp))
                                        Text(
                                            text = post.content,
                                            style = MaterialTheme.typography.body1
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is UIState.Error -> {
                        item {
                            LaunchedEffect(scaffoldState) {
                                postDetailState.message?.let {
                                    scaffoldState.snackbarHostState.showSnackbar(it)
                                }
                            }
                        }
                    }

                    else -> {}
                }

                item {
                    Divider()
                }

                commentsSection(
                    commentsState = commentsState,
                    comments = comments
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Divider()
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.Transparent)
                        .onFocusChanged { isCommentFocused = it.isFocused },
                    value = commentContent,
                    onValueChange = { onEvent(CommentEvent.OnCommentContentChanged(it)) },
                    cursorBrush = SolidColor(MaterialTheme.colors.primary),
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground
                    ),
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (commentContent.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.write_comment),
                                    color = Grey3
                                )
                            }

                            innerTextField()
                        }
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    modifier = Modifier.size(24.dp),
                    enabled = commentContent.isNotEmpty(),
                    onClick = {
                        onEvent(CommentEvent.UploadComment)
                        onEvent(CommentEvent.OnCommentContentChanged(""))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = "Send comment icon"
                    )
                }
            }
        }
    }
}

private fun LazyListScope.commentsSection(
    commentsState: UIState<Nothing>,
    comments: List<Comment>
) {
    when (commentsState) {
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
            items(comments) { comment ->
                CommentItemCard(comment)
            }
        }

        else -> {}
    }
}