package com.example.ui.components.atoms

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.theme.typography

@Composable
fun Header(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = typography.headingExtraLarge,
        modifier = modifier,
    )
}