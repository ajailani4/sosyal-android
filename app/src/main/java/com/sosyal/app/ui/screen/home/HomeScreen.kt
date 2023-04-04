package com.sosyal.app.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import com.sosyal.app.ui.screen.home.component.PostItemCard
import com.sosyal.app.ui.theme.SosyalTheme
import com.sosyal.app.ui.theme.backgroundGrey

@Composable
fun HomeScreen() {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.backgroundGrey)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            (1..10).forEach { _ ->
                PostItemCard()
                Spacer(modifier = Modifier.height(10.dp))
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