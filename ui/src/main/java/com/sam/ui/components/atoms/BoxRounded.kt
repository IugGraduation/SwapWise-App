package com.sam.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.sam.ui.theme.PrimaryOverlay
import com.sam.ui.theme.RadiusLarge

@Composable
fun BoxRounded(
    color: Color = PrimaryOverlay,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(RadiusLarge))
            .background(color = color),
        contentAlignment = contentAlignment,
    ) {
        content()
    }
}