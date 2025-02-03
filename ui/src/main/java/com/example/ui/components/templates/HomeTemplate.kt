package com.example.ui.components.templates

import androidx.compose.runtime.Composable
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.model.UiState
import com.example.ui.components.molecules.HomeTopBar
import com.example.ui.models.BottomBarUiState
import com.example.domain.model.User

@Composable
fun HomeTemplate(
    user: User,
    bottomBarState: BottomBarUiState,
    contentState: UiState = object : UiState {
        override val isLoading: Boolean = false
        override val error: String? = null
    },
    content: @Composable () -> Unit
) {
    BottomBarTemplate(
        topBar = {
            HomeTopBar(
                title = "Good Morning \uD83D\uDC4B",
                subtitle = user.name,
                imagePainter = rememberAsyncImagePainter(user.imageLink),
            )
        },
        bottomBarState = bottomBarState,
        contentState = contentState,
    ) {
        content()
    }
}

