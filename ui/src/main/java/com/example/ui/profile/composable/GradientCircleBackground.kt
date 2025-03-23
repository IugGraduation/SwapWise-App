package com.example.ui.profile.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.translate
import com.example.ui.theme.GradientBrush

@Composable
fun GradientCircleBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val circleRadius = canvasWidth * 0.9f // 70% of the width
        val yOffset = -canvasHeight * 0.9f  // Moves circle upwards by 30% of screen height

        translate(left = 0f, top = yOffset) {
            drawCircle(brush = GradientBrush, radius = circleRadius)
        }
    }
}