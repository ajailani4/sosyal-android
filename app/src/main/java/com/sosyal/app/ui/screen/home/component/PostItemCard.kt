package com.sosyal.app.ui.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sosyal.app.R
import com.sosyal.app.ui.theme.Grey

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItemCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        elevation = 0.dp,
        onClick = {}
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "User profile picture"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "George Zayvich",
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "1 hour ago",
                        style = MaterialTheme.typography.caption.copy(color = Grey)
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Bang kalau kita naik motor, motor yang bawa kita atau kita yang bawa motor?",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                SmallCircleButton(
                    icon = Icons.Default.ThumbUp,
                    text = "10 Likes",
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(20.dp))
                SmallCircleButton(
                    icon = Icons.Default.ChatBubble,
                    text = "10 Comments",
                    onClick = {}
                )
            }
        }
    }
}