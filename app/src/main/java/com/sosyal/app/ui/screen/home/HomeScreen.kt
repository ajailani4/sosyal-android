package com.sosyal.app.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sosyal.app.R
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.common.component.ProgressBarWithBackground
import com.sosyal.app.ui.screen.home.component.PostItemCard
import com.sosyal.app.ui.theme.SosyalTheme
import com.sosyal.app.ui.theme.backgroundGrey
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val homeState = homeViewModel.homeState
    val posts = homeViewModel.posts.reversed()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Surface(elevation = 4.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.surface)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sosyal_text_logo),
                        contentDescription = "Sosyal text logo"
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedButton(
                            modifier = Modifier.size(width = 35.dp, height = 35.dp),
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colors.primary
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                            contentPadding = PaddingValues(0.dp),
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add post icon"
                            )
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Profile picture"
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.backgroundGrey)
                .padding(innerPadding)
        ) {
            when (homeState.uiState) {
                UIState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 170.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is UIState.Success -> {
                    items(posts) { post ->
                        PostItemCard(post)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    SosyalTheme {
        HomeScreen()
    }
}