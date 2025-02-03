package com.example.ui.components.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing32
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun DetailsScreenBody(title: String, body: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = Spacing16, end = Spacing32)) {
        Text(
            text = title,
            style = TextStyles.headingLarge,
            color = MaterialTheme.colorScheme.primary
        )
        VerticalSpacer(Spacing8)
        Text(
            text = body,
            style = TextStyles.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}