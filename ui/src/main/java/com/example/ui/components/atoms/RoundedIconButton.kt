package com.example.ui.components.atoms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.ui.theme.IconSizeLarge
import com.example.ui.theme.IconSizeSmall
import com.example.ui.theme.Primary

@Composable
fun RoundedIconButton(onClick: () -> Unit, iconResId: Int, contentDescription: String = "", modifier: Modifier = Modifier) {
    BoxRounded(
        color = Primary,
        modifier = modifier.size(IconSizeLarge).clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painterResource(iconResId),
            contentDescription = contentDescription,
            modifier = Modifier.size(IconSizeSmall),
            tint = Color.White
        )
    }
}