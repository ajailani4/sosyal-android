package com.sosyal.app.ui.screen.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sosyal.app.R
import com.sosyal.app.domain.model.Post
import com.sosyal.app.ui.common.component.formatDateTime
import com.sosyal.app.ui.theme.Grey
import com.sosyal.app.ui.theme.Grey3
import com.sosyal.app.util.Formatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItemCard(
    post: Post,
    onCardClicked: () -> Unit,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onMoreClicked: () -> Unit
) {
    Card(
        shape = RectangleShape,
        elevation = 0.dp,
        onClick = onCardClicked
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    AsyncImage(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(post.userAvatar ?: R.drawable.img_default_ava)
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
                            text = formatDateTime(
                                Formatter.convertStringToDateOrTime(post.date)
                            ),
                            style = MaterialTheme.typography.caption.copy(color = Grey)
                        )
                    }
                }
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onMoreClicked
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        tint = Grey3,
                        contentDescription = "More icon"
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = post.content,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                SmallCircleButton(
                    icon = Icons.Default.ThumbUp,
                    text = stringResource(
                        id = if (post.likes > 1) R.string.likes else R.string.like,
                        post.likes
                    ),
                    isActive = post.isLiked,
                    onClick = onLikeClicked
                )
                Spacer(modifier = Modifier.width(20.dp))
                SmallCircleButton(
                    icon = Icons.Default.ChatBubble,
                    text = stringResource(
                        id = if (post.comments > 1) R.string.comments else R.string.comment,
                        post.comments
                    ),
                    onClick = onCommentClicked
                )
            }
        }
    }
}