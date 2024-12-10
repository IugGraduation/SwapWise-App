package com.example.ui.components.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ui.theme.Spacing4
import com.example.ui.theme.TextStyles

@Composable
fun PostDetailsStatusItem(title: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            style = TextStyles.headingSmall,
            color = MaterialTheme.colorScheme.primary
        )
        VerticalSpacer(Spacing4)
        Text(
            title,
            style = TextStyles.captionLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}