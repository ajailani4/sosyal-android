package com.sosyal.app.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sosyal.app.R
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.theme.Grey3
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = koinViewModel(),
    onNavigateUp: () -> Unit
) {
    val userProfileState = profileViewModel.userProfileState

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
                    Text(text = stringResource(id = R.string.profile))
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "Log out icon"
                        )
                    }
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
                    userProfileState.data?.let { userProfile ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(125.dp)
                                    .clip(CircleShape),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(userProfile.avatar ?: R.drawable.img_default_ava)
                                    .placeholder(R.drawable.img_default_ava)
                                    .build(),
                                contentDescription = "User profile avatar"
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "George Zayvich",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.subtitle1

                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "george_z",
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "george_zayvich@email.com",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body2.copy(
                                    color = Grey3
                                )
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.medium,
                                onClick = { /*TODO*/ }
                            ) {
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    text = stringResource(id = R.string.edit_profile),
                                    textAlign = TextAlign.Center
                                )
                            }
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
    }
}
