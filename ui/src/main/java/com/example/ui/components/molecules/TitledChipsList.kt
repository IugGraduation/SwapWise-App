package com.example.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.models.Chip
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Secondary
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun TitledChipsList(
    title: String? = null,
    textStyle: TextStyle = TextStyles.headingMedium,
    chipsList: List<Chip>
) {
    if (title != null) {
        Text(
            text = title,
            style = textStyle,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = Spacing16)
        )
        VerticalSpacer(Spacing8)
    }
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing16),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing4)
    ) {
        items(chipsList) {
            SwapWiseChip(it)
        }
    }
}

@Composable
fun SwapWiseChip(chip: Chip, modifier: Modifier = Modifier) {
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
        modifier = myModifier.clickable(enabled = chip.clickable) {
            chip.selected = !chip.selected
            chip.onClick(chip.text)
        },
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