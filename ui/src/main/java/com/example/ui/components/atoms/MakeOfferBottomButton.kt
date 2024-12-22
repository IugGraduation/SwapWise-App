package com.example.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.ui.R
import com.example.ui.theme.IconSizeSmall
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryOverlay
import com.example.ui.theme.Spacing4
import com.example.ui.theme.TextStyles

@Composable
fun ButtonMakeOfferBottom(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(color = PrimaryOverlay)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing4),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_offer),
                contentDescription = "",
                modifier = Modifier.size(IconSizeSmall),
                tint = Primary
//                  todo: draw stroke with smth like: drawStyle = Stroke(width = 1.2f, join = StrokeJoin.Round)
            )
            HorizontalSpacer(Spacing4)
            Text(
                stringResource(R.string.make_your_offer),
                style = TextStyles.captionMedium,
                color = Primary
            )
        }
    }
}