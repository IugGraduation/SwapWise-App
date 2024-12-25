package com.example.ui.components.templates

import androidx.compose.runtime.Composable
import com.example.domain.model.ContentState
import com.example.ui.components.atoms.CustomBottomNavigationBar
import com.example.ui.models.BottomBarUiState

@Composable
fun BottomBarTemplate(
    bottomBarState: BottomBarUiState,
    topBar: @Composable () -> Unit = {},
    contentState: ContentState = object : ContentState {
        override val isLoading: Boolean = false
        override val error: String? = null
    },
    content: @Composable () -> Unit,
) {
    ScreenTemplate(
        topBar = topBar,
        bottomBar = {
            CustomBottomNavigationBar(
                selectedItem = bottomBarState.selectedItem,
                onClickItem = { item ->
                    bottomBarState.onItemSelected(item)
                    when (item) {
                        0 -> bottomBarState.navigateToHome()
                        1 -> bottomBarState.navigateToSearch()
                        2 -> bottomBarState.navigateToNotifications()
                        3 -> bottomBarState.navigateToProfile()
                    }
                }
            )
        },
        contentState = contentState,
    ) {
        content()
    }
}

