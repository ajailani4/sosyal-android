package com.sosyal.app.ui.screen.upload_edit_post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sosyal.app.R
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.common.component.ProgressBarWithBackground
import com.sosyal.app.ui.theme.Grey3
import org.koin.androidx.compose.koinViewModel

@Composable
fun UploadEditPostScreen(
    uploadEditPostViewModel: UploadEditPostViewModel = koinViewModel(),
    onNavigateUp: () -> Unit
) {
    val onEvent = uploadEditPostViewModel::onEvent
    val uploadPostState = uploadEditPostViewModel.uploadPostState
    val content = uploadEditPostViewModel.content
    var isContentFocused by remember { mutableStateOf(false) }

    Scaffold(
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
                        text = stringResource(id = R.string.upload_post),
                        style = MaterialTheme.typography.h2
                    )
                },
                actions = {
                    IconButton(
                        enabled = content.isNotEmpty(),
                        onClick = { onEvent(UploadEditPostEvent.UploadPost) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "Submit icon"
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.background
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                        .onFocusChanged { isContentFocused = it.isFocused },
                    value = content,
                    onValueChange = { onEvent(UploadEditPostEvent.OnContentChanged(it)) },
                    cursorBrush = SolidColor(MaterialTheme.colors.primary),
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground
                    ),
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (content.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.write_something),
                                    color = Grey3
                                )
                            }

                            innerTextField()
                        }
                    }
                )
            }
        }

        when (uploadPostState) {
            is UIState.Success -> {
                onNavigateUp()
            }

            else -> {}
        }
    }
}