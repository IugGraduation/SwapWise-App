package com.example.ui.components.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

@Composable
fun ImageWithMaxWidth(
    painter: Painter,
    contentDescription: String?,
    painterDarkTheme: Painter = painter,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val imagePainter = if(isSystemInDarkTheme()) painterDarkTheme else painter
    Image(
        painter = imagePainter,
        contentDescription = contentDescription,
        modifier = modifier.fillMaxWidth(),
        contentScale = contentScale
    )
}