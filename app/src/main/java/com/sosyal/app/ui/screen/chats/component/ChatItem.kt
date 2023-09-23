package com.sosyal.app.ui.screen.chats.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.sosyal.app.ui.theme.Grey4

@Composable
fun ChatItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.img_default_ava)
                .placeholder(R.drawable.img_default_ava)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "User profile picture"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = "Username",
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Message",
                style = MaterialTheme.typography.body1.copy(
                    color = Grey4
                )
            )
        }
    }
}