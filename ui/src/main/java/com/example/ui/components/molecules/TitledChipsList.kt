package com.example.ui.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.ui.components.atoms.CustomChip
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.models.Chip
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
            CustomChip(it)
        }
    }
}