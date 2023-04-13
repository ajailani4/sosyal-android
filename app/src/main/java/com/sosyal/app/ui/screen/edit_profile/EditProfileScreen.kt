package com.sosyal.app.ui.screen.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Mail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sosyal.app.R
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.common.component.ProgressBarWithBackground
import com.sosyal.app.ui.theme.SosyalTheme
import com.sosyal.app.util.convertInputStreamToFile
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProfileScreen(
    editProfileViewModel: EditProfileViewModel = koinViewModel(),
    onNavigateUp: () -> Unit
) {
    val onEvent = editProfileViewModel::onEvent
    val userProfileState = editProfileViewModel.userProfileState
    val editProfileState = editProfileViewModel.editProfileState
    val username = editProfileViewModel.username
    val name = editProfileViewModel.name
    val email = editProfileViewModel.email
    val avatar = editProfileViewModel.avatar

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val inputStream = context.contentResolver.openInputStream(uri)

                if (inputStream != null) {
                    coroutineScope.launch {
                        onEvent(
                            EditProfileEvent.OnAvatarChanged(
                                context.convertInputStreamToFile(inputStream)
                            )
                        )
                    }
                }
            }
        }
    )

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
                        text = stringResource(id = R.string.edit_profile),
                        style = MaterialTheme.typography.h2
                    )
                },
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            when (userProfileState) {
                UIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UIState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box {
                            AsyncImage(
                                modifier = Modifier
                                    .size(125.dp)
                                    .clip(CircleShape),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(avatar ?: R.drawable.img_default_ava)
                                    .placeholder(R.drawable.img_default_ava)
                                    .build(),
                                contentDescription = "User profile avatar"
                            )
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(color = MaterialTheme.colors.primary)
                                    .align(Alignment.BottomEnd)
                                    .clickable {
                                        pickMediaLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(10.dp),
                                    imageVector = Icons.Default.Edit,
                                    tint = MaterialTheme.colors.onPrimary,
                                    contentDescription = "Edit icon"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = username,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = name,
                            onValueChange = { onEvent(EditProfileEvent.OnNameChanged(it)) },
                            label = {
                                Text(text = stringResource(id = R.string.name))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Badge,
                                    contentDescription = "Name icon"
                                )
                            },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email,
                            onValueChange = { onEvent(EditProfileEvent.OnEmailChanged(it)) },
                            label = {
                                Text(text = stringResource(id = R.string.email))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Mail,
                                    contentDescription = "Email icon"
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            )
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            onClick = {
                                onEvent(EditProfileEvent.EditProfile)
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = stringResource(id = R.string.save),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                is UIState.Error -> {
                    LaunchedEffect(scaffoldState) {
                        userProfileState.message?.let {
                            scaffoldState.snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                else -> {}
            }
        }

        when (editProfileState) {
            UIState.Loading -> ProgressBarWithBackground()

            is UIState.Success -> {
                LaunchedEffect(Unit) {
                    onNavigateUp()
                }
            }

            is UIState.Error -> {
                LaunchedEffect(scaffoldState) {
                    editProfileState.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }
}

@Preview
@Composable
fun PreviewEditProfileScreen() {
    SosyalTheme {
        EditProfileScreen {}
    }
}