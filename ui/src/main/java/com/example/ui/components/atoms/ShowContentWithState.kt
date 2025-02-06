package com.example.ui.components.atoms

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ui.base.BaseUiState
import com.example.ui.theme.BlackTertiary


@Composable
fun ShowContentWithState(state: BaseUiState, content: @Composable () -> Unit) {
    when {
        state.isLoading -> {
            Box {
                Column(modifier = Modifier.fillMaxSize()) {
                    content()
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BlackTertiary),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        state.errorMessage.isNotBlank() -> {
            Toast.makeText(LocalContext.current, state.errorMessage, Toast.LENGTH_LONG).show()
            content()
        }

        else -> {
            content()
        }
    }
}