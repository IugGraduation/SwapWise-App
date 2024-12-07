package com.example.ui.components.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles


@Composable
fun HomeTopBar(
    title: String,
    subtitle: String,
    imagePainter: Painter,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing16),
        contentAlignment = Alignment.CenterStart
    ) {
        Row {
            Image(
                painter = imagePainter,
                contentDescription = stringResource(R.string.user_image),
                modifier = Modifier.size(40.dp)
            )
            HorizontalSpacer(Spacing8)
            Column (modifier = Modifier.padding(vertical = Spacing8)) {
                Text(
                    text = title,
                    style = TextStyles.captionLarge,
                )
                VerticalSpacer(Spacing4)
                Text(
                    text = subtitle,
                    style = TextStyles.headingSmall,
                )
            }
            VerticalSpacer(Spacing24)
        }
    }
}