package com.example.ui.components

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
import com.example.ui.R
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.models.AsyncState
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

/**
 * A generic composable that handles the four common states of an asynchronous operation:
 * Loading, Error, Success, and Empty Success.
 */
@Composable
fun <T> AsyncContent(
    state: AsyncState<T>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    successContent: @Composable (T) -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (state) {
            is AsyncState.Loading, AsyncState.Initial -> {
                CircularProgressIndicator()
            }

            is AsyncState.Error -> {
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
                        text = state.message,
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

            is AsyncState.Success -> {
                val data = state.data
                // Check if the successful data is a Collection and is empty.
                if (data is Collection<*> && data.isEmpty()) {
                    // If the data is an empty list, show a generic message.
                    Text(
                        text = stringResource(id = R.string.no_items_found),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    // Otherwise, show the main success content.
                    successContent(data)
                }
            }
        }
    }
}
