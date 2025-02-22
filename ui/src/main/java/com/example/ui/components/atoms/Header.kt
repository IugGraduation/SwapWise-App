package com.example.ui.components.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.example.ui.theme.Spacing24
import com.example.ui.theme.TextStyles

@Composable
fun Header(
    title: String,
    imgPainter: Painter,
    imgContentDescription: String?,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    imgPainterDarkTheme: Painter = imgPainter,
) {
    //todo: change the way of checking dark theme
    Image(
        painter =  if(isDarkTheme) imgPainterDarkTheme else imgPainter,
        contentDescription = imgContentDescription,
        modifier = modifier.fillMaxWidth(),
    )
    VerticalSpacer(Spacing24)
    Text(
        text = title,
        style = TextStyles.headingExtraLarge,
        modifier = modifier,
    )
}