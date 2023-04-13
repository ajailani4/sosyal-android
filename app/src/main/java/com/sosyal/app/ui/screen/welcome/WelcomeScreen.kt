package com.sosyal.app.ui.screen.welcome

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sosyal.app.R

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.welcome),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary
            )
        )
        Image(
            modifier = Modifier.size(width = 150.dp, height = 90.dp),
            painter = painterResource(id = R.drawable.sosyal_text_logo_white),
            contentDescription = "Sosyal text logo"
        )
        Image(
            painter = painterResource(id = R.drawable.welcome_illustration),
            contentDescription = "Welcome illustration"
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            onClick = onNavigateToLogin
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = stringResource(id = R.string.login),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.onPrimary
            ),
            onClick = onNavigateToRegister
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = stringResource(id = R.string.register),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}