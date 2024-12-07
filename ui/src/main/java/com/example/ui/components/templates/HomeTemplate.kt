package com.example.ui.components.templates

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.ui.components.molecules.HomeTopBar
import com.example.ui.models.BottomBarUiState
import com.example.domain.models.User

@Composable
fun HomeTemplate(
    user: User,
    bottomBarState: BottomBarUiState,
    content: @Composable () -> Unit
) {
    BottomBarTemplate(
        topBar = {
            HomeTopBar(
                title = "Good Morning \uD83D\uDC4B",
                subtitle = user.name,
                imagePainter = painterResource(user.imgResId),
            )
        },
        bottomBarState = bottomBarState,
    ) {
        content()
    }
}

