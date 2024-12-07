package com.example.ui.components.molecules

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.TextStatus
import com.example.ui.theme.CorrectOverlay
import com.example.ui.theme.DangerOverlay

@Composable
fun BoxStatus(isOpen: Boolean, modifier: Modifier = Modifier) {
    val color = if (isOpen) CorrectOverlay else DangerOverlay
    BoxRounded(
        color = color,
        modifier = modifier
    ) {
        TextStatus(isOpen = isOpen)
    }
}