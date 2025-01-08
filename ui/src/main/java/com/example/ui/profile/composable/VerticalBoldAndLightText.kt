package com.example.ui.profile.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.ui.theme.BlackPrimary
import com.example.ui.theme.BlackTertiary
import com.example.ui.theme.Spacing4
import com.example.ui.theme.TextStyles

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
            color = BlackPrimary
        )
        Text(
            text = lightText,
            style = TextStyles.captionLarge,
            color = BlackTertiary
        )
    }

}