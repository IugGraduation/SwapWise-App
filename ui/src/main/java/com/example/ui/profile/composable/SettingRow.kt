package com.example.ui.profile.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.ui.R
import com.example.ui.theme.BlackSecondary
import com.example.ui.theme.IconSizeMedium
import com.example.ui.theme.NormalButtonHeight
import com.example.ui.theme.RadiusMedium
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.WhitePrimary


@Composable
fun SettingsRow(
    title: String,
    onRowClick: () -> Unit,
    contentColor: Color = BlackSecondary,
    leadingIconResource: Painter,
    trailingIcon: @Composable (() -> Unit) = { ArrowIcon(contentColor = contentColor) },
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = with(modifier) {
            fillMaxWidth()
                .height(NormalButtonHeight)
                .clip(RoundedCornerShape(RadiusMedium))
                .clickable { onRowClick() }
                .background(color = WhitePrimary)
                .padding(horizontal = Spacing8)
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.Start) {
            Icon(
                modifier = Modifier.size(IconSizeMedium),
                painter = leadingIconResource,
                contentDescription = title,
                tint = contentColor
            )

            Text(
                modifier = Modifier.padding(start = Spacing8),
                text = title,
                style = TextStyles.bodyLarge,
                color = contentColor
            )
        }

        trailingIcon()
    }
}

@Composable
fun ArrowIcon(contentColor: Color) {
    Icon(
        modifier = Modifier.size(IconSizeMedium),
        painter = painterResource(id = R.drawable.ic_arrow_left),
        contentDescription = null,
        tint = contentColor
    )
}

