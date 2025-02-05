package com.example.ui.components.atoms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign


@Composable
fun CardText(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        style = textStyle.copy(
            shadow = Shadow(
                color = Color(0x99000000),
                offset = Offset(4f, 4f),
                blurRadius = 8f
            ),
            color = MaterialTheme.colorScheme.primary,
            drawStyle = Stroke(width = 1.2f, join = StrokeJoin.Round),
        ),
        textAlign = TextAlign.Center,
    )
}