package com.example.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.profile.composable.ProfileImage
import com.example.ui.theme.GradientBackground
import com.example.ui.theme.Primary
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun ProfileScreen(
    navController: NavController,
) {
    ProfileContent(
        state = TODO(),
        profileInteraction = TODO()
    )
}

@Composable
private fun ProfileContent(
    state: ProfileUiState,
    profileInteraction: ProfileInteraction,
    ) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = false
    )

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RectangleShape)
                .background(brush = GradientBackground)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {
            Text(
                text = "Profile",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp)
            )
            ProfileImage(
                state = state,
                onImageChangeClick = profileInteraction::onUpdateProfileImage
            )
        }
    }
}



@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileContent()
}

