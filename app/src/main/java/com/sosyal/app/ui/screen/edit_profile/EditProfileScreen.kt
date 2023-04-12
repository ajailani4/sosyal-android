package com.sosyal.app.ui.screen.edit_profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sosyal.app.R
import com.sosyal.app.ui.screen.register.RegisterEvent
import com.sosyal.app.ui.theme.Grey2
import com.sosyal.app.ui.theme.Grey3
import com.sosyal.app.ui.theme.SosyalTheme

@Composable
fun EditProfileScreen(
    onNavigateUp: () -> Unit
) {
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
                    Text(text = stringResource(id = R.string.edit_profile))
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*AsyncImage(
                    modifier = Modifier
                        .size(125.dp)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userProfile.avatar ?: R.drawable.img_default_ava)
                        .placeholder(R.drawable.img_default_ava)
                        .build(),
                    contentDescription = "User profile avatar"
                )*/
                Box {
                    Image(
                        modifier = Modifier
                            .size(125.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.img_default_ava),
                        contentDescription = "User profile avatar"
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colors.primary)
                            .align(Alignment.BottomEnd)
                            .clickable { }
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
                    text = "george_z",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChange = { },
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
                    value = "",
                    onValueChange = { },
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
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = stringResource(id = R.string.save),
                        textAlign = TextAlign.Center
                    )
                }
            }
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