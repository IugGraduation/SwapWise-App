package com.sam.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sam.ui.R
import com.sam.ui.components.atoms.SwapWiseFilledButton
import com.sam.ui.components.atoms.VerticalSpacer
import com.sam.ui.models.AsyncState
import com.sam.ui.theme.Spacing16
import com.sam.ui.theme.Spacing8
import com.sam.ui.theme.TextStyles
import com.sam.ui.theme.color

/**
 * A generic composable that handles the four common states of an asynchronous operation:
 * Loading, Error, Success, and Empty Success.
 */
@Composable
fun <T> AsyncContent(
    state: AsyncState<T>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    initialContent: @Composable () -> Unit = { DefaultLoadingContent() },
    loadingContent: @Composable () -> Unit = { DefaultLoadingContent() },
    errorContent: @Composable (String) -> Unit = { message -> DefaultErrorContent(message, onRetry) },
    emptyContent: @Composable () -> Unit = { DefaultEmptyContent() },
    successContent: @Composable (T) -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (state) {
            AsyncState.Initial -> initialContent()

            AsyncState.Loading -> loadingContent()

            is AsyncState.Error -> errorContent(state.message)

            is AsyncState.Success -> {
                val data = state.data
                if (data is Collection<*> && data.isEmpty()) {
                    emptyContent()
                } else {
                    successContent(data)
                }
            }
        }
    }
}

@Composable
private fun DefaultLoadingContent() {
    CircularProgressIndicator()
}

@Composable
private fun DefaultErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(Spacing16)
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.color.textTertiary
        )
        VerticalSpacer(Spacing16)
        Text(
            text = message,
            style = TextStyles.bodyLarge,
            color = MaterialTheme.color.textPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(Spacing8))
        SwapWiseFilledButton(
            onClick = onRetry,
            text = stringResource(R.string.retry),
            modifier = Modifier.fillMaxWidth(0.6f)
        )
    }
}

@Composable
private fun DefaultEmptyContent() {
    Text(
        text = stringResource(id = R.string.no_items_found),
        style = MaterialTheme.typography.bodyLarge
    )
}
