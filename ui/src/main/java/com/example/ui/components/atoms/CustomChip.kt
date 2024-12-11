package com.example.ui.components.atoms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.models.Chip
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Secondary
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun CustomChip(chip: Chip, modifier: Modifier = Modifier) {
    val myModifier = if (!chip.selected) modifier.shadow(
        elevation = 4.dp,
        shape = RoundedCornerShape(RadiusLarge),
        spotColor = Color.Black.copy(alpha = 0.8f),
        ambientColor = Color.Black.copy(alpha = 0.8f)
    ) else modifier
    val backgroundColor = if (chip.selected) Secondary else MaterialTheme.colorScheme.onBackground
    val textColor =
        if (chip.selected && isSystemInDarkTheme()) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.secondary

    BoxRounded(
        modifier = myModifier.clickable { chip.onClick() },
        color = backgroundColor,
    ) {
        Text(
            chip.text,
            style = TextStyles.hint,
            color = textColor,
            modifier = Modifier.padding(horizontal = Spacing16, vertical = Spacing8)
        )
    }
}