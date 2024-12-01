package com.example.ui.components.atoms

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.theme.Danger
import com.example.ui.theme.TextStyles

@Composable
fun ErrorText(text: String = "", modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = TextStyles.captionMedium.copy(color = Danger),
        modifier = modifier
    )
}