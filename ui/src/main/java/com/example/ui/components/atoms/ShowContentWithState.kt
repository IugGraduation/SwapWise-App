package com.example.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.base.BaseUiState
import com.example.ui.theme.BlackTertiary
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color


@Composable
fun ShowContentWithState(
    state: BaseUiState,
    snackbarHostState: SnackbarHostState,
    onRetry: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var lastErrorMessage by remember { mutableStateOf("") }

    // Reset error when a new load starts to prevent the error screen 
    // from flashing when loading a second piece of data.
    LaunchedEffect(state.isLoading) {
        if (state.isLoading) {
            lastErrorMessage = ""
        }
    }

    LaunchedEffect(state.errorSharedFlow) {
        state.errorSharedFlow.collect { errorMessage ->
            lastErrorMessage = errorMessage
            if (!state.shouldHideContent) {
                snackbarHostState.showSnackbar(errorMessage)
            }
        }
    }

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            if (state.shouldHideContent.not()) {
                content()
            } else if (!state.isLoading && lastErrorMessage.isNotEmpty()) {
                ErrorStateContent(
                    errorMessage = lastErrorMessage,
                    onRetry = onRetry
                )
            }
        }
        
        if (state.isLoading) {
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
}

@Composable
private fun ErrorStateContent(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.color.textTertiary
        )
        VerticalSpacer(Spacing16)
        Text(
            text = errorMessage,
            style = TextStyles.bodyLarge,
            color = MaterialTheme.color.textPrimary,
            textAlign = TextAlign.Center
        )
        VerticalSpacer(Spacing8)
        SwapWiseFilledButton(
            onClick = onRetry,
            text = stringResource(R.string.try_again),
            modifier = Modifier.fillMaxWidth(0.6f)
        )
    }
}
