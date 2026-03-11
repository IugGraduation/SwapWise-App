package com.sam.ui.profile.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.sam.ui.theme.Spacing4
import com.sam.ui.theme.TextStyles
import com.sam.ui.theme.color

@Composable
fun VerticalBoldAndLightText(
    modifier: Modifier = Modifier,
    boldText: String,
    lightText: String,
    boldStyle: TextStyle = TextStyles.headingSmall) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = boldText,
            style = boldStyle,
            color = MaterialTheme.color.textPrimary
        )
        Text(
            text = lightText,
            style = TextStyles.captionLarge,
            color = MaterialTheme.color.textTertiary
        )
    }

}