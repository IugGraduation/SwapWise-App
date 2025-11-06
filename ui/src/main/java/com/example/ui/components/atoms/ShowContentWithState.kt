package com.example.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.ui.base.BaseUiState
import com.example.ui.theme.BlackTertiary
import com.example.ui.theme.color


@Composable
fun ShowContentWithState(
    state: BaseUiState,
    snackbarHostState: SnackbarHostState,
    content: @Composable () -> Unit
) {
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            if (state.shouldHideContent.not()) {
                content()
            }
        }
        when {
            state.isLoading -> {
                val backgroundColor =
                    if (state.shouldHideContent.not()) BlackTertiary else Color.Transparent
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.color.primary)
                }
            }
        }
        LaunchedEffect(state.errorSharedFlow) {
            state.errorSharedFlow.collect { errorMessage ->
                snackbarHostState.showSnackbar(errorMessage)
            }
        }

    }
}