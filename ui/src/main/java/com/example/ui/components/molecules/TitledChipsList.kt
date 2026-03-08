package com.example.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.ui.R
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.models.AsyncState
import com.example.ui.models.ChipUiState
import com.example.ui.theme.IconSize48
import com.example.ui.theme.IconSize24
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Secondary
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing2
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun TitledChipsList(
    title: String,
    textStyle: TextStyle = TextStyles.headingMedium,
    state: AsyncState<List<ChipUiState>>,
    onRetry: () -> Unit = {}
) {
    Text(
        text = title,
        style = textStyle,
        color = MaterialTheme.color.textPrimary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing16),
    )

    VerticalSpacer(Spacing8)

    when (state) {
        is AsyncState.Loading, AsyncState.Initial -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IconSize48),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(IconSize24),
                    strokeWidth = Spacing2,
                    color = MaterialTheme.color.primary
                )
            }
        }

        is AsyncState.Success -> {
            LazyRow(
                contentPadding = PaddingValues(horizontal = Spacing16),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing4)
            ) {
                items(items = state.data, key = { it.categoryItem.id }) {
                    SwapWiseChip(it)
                }
            }
        }

        is AsyncState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IconSize48),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.retry),
                    tint = MaterialTheme.color.danger,
                    modifier = Modifier
                        .size(IconSize24)
                        .clickable { onRetry() }
                )
            }
        }
    }
}

@Composable
private fun SwapWiseChip(chip: ChipUiState, modifier: Modifier = Modifier) {
    val myModifier = if (!chip.selected.value) modifier.shadow(
        elevation = Spacing4,
        shape = RoundedCornerShape(RadiusLarge),
        spotColor = Color.Black.copy(alpha = 0.8f),
        ambientColor = Color.Black.copy(alpha = 0.8f)
    ) else modifier
    val backgroundColor = if (chip.selected.value) Secondary else MaterialTheme.color.onBackground
    val textColor =
        if (chip.selected.value && isSystemInDarkTheme()) MaterialTheme.color.background else MaterialTheme.color.textSecondary

    BoxRounded(
        modifier = myModifier.clickable(enabled = chip.clickable) {
            chip.selected.value = !chip.selected.value
            chip.onClick(chip.categoryItem)
        },
        color = backgroundColor,
    ) {
        Text(
            chip.categoryItem.name,
            style = TextStyles.hint,
            color = textColor,
            modifier = Modifier.padding(horizontal = Spacing16, vertical = Spacing8)
        )
    }
}
