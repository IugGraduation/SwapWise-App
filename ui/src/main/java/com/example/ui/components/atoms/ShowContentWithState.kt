package com.example.ui.components.atoms

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.domain.model.ContentState
import com.example.ui.theme.BlackTertiary
import com.example.ui.theme.Spacing16
import com.example.ui.theme.TextStyles


@Composable
fun ShowContentWithState(state: ContentState, content: @Composable () -> Unit) {
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

        state.error != null -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text(
//                    text = "Error: ${state.error}",
//                    style = TextStyles.headingSmall,
//                    modifier = Modifier.padding(Spacing16)
//                )
//            }
            Toast.makeText(LocalContext.current, "state.error", Toast.LENGTH_LONG).show()
            content()
        }

        else -> {
            content()
        }
    }
}