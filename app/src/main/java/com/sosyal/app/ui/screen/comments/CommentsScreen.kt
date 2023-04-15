package com.sosyal.app.ui.screen.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
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
import com.sosyal.app.ui.screen.comments.component.CommentItemCard
import com.sosyal.app.ui.screen.upload_edit_post.UploadEditPostEvent
import com.sosyal.app.ui.theme.Grey
import com.sosyal.app.ui.theme.Grey3
import com.sosyal.app.util.Formatter
import com.sosyal.app.util.comments

@Composable
fun CommentsScreen() {
    val (comment, setComment) = remember { mutableStateOf("") }
    var isCommentFocused by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
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
                                        .data(/*post.userAvatar ?: */R.drawable.img_default_ava)
                                        .placeholder(R.drawable.img_default_ava)
                                        .build(),
                                    contentDescription = "User profile picture"
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = /*post.username!!*/"George Z",
                                        style = MaterialTheme.typography.subtitle1
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = Formatter.convertStringToDateOrTime(/*post.date!!*/"2023-04-15 20:20")
                                            .run {
                                                when {
                                                    seconds in (0..59) -> {
                                                        stringResource(id = R.string.just_now)
                                                    }

                                                    minutes in (1..59) -> {
                                                        stringResource(
                                                            id = if (minutes > 1L) {
                                                                R.string.minutes_ago
                                                            } else {
                                                                R.string.minute_ago
                                                            },
                                                            minutes
                                                        )
                                                    }

                                                    hours in (1..23) -> {
                                                        stringResource(
                                                            id = if (hours > 1L) {
                                                                R.string.hours_ago
                                                            } else {
                                                                R.string.hour_ago
                                                            },
                                                            hours
                                                        )
                                                    }

                                                    days == 1L -> stringResource(id = R.string.yesterday)

                                                    else -> date
                                                }
                                            },
                                        style = MaterialTheme.typography.caption.copy(color = Grey)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = /*post.content!!*/"Hello",
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }

                item {
                    Divider()
                }

                commentsSection()
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
                    value = comment,
                    onValueChange = setComment,
                    cursorBrush = SolidColor(MaterialTheme.colors.primary),
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground
                    ),
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (comment.isEmpty()) {
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
                    onClick = { /*TODO*/ }
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

private fun LazyListScope.commentsSection() {
    items(comments) { comment ->
        CommentItemCard(comment)
    }
}