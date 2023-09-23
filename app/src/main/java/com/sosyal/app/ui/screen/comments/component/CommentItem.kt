package com.sosyal.app.ui.screen.comments.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sosyal.app.R
import com.sosyal.app.domain.model.Comment

@Composable
fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(comment.userAvatar ?: R.drawable.img_default_ava)
                .placeholder(R.drawable.img_default_ava)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "User profile picture"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = comment.username,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = comment.content,
                style = MaterialTheme.typography.body1
            )
        }
    }
}