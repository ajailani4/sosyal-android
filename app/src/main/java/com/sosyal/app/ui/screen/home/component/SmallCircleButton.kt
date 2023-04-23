package com.sosyal.app.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sosyal.app.ui.theme.Grey2
import com.sosyal.app.ui.theme.Grey3

@Composable
fun SmallCircleButton(
    icon: ImageVector,
    text: String,
    isActive: Boolean? = null,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = if (isActive == true) MaterialTheme.colors.secondary else Grey2)
                .clickable { onClick() }
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .padding(7.dp),
                imageVector = icon,
                tint = if (isActive == true) MaterialTheme.colors.onSecondary else Grey3,
                contentDescription = "Like icon"
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.body2
        )
    }
}