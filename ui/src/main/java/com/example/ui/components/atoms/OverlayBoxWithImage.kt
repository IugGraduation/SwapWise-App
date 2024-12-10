package com.example.ui.components.atoms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ui.theme.Spacing4
import com.example.ui.theme.TextStyles

@Composable
fun OverlayBoxWithImage(overlayColor: Color, imgResId: Int, text: String, modifier: Modifier = Modifier, boxModifier: Modifier = Modifier) {
    BoxRounded(color = overlayColor, modifier = boxModifier) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(imgResId),
                contentDescription = "",
                modifier = Modifier.size(12.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            HorizontalSpacer(Spacing4)
            Text(text, style = TextStyles.captionSmall)
        }
    }
}