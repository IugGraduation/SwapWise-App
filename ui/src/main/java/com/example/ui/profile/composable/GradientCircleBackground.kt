package com.example.ui.profile.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GradientBrush
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.translate

@Composable
fun GradientCircleBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        translate(left = 0f, top = -900f) {
            drawCircle(brush = GradientBrush, radius = 560.dp.toPx())
        }
    }
}