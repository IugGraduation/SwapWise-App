package com.example.ui.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.domain.models.IOffer
import com.example.domain.models.PostItem
import com.example.ui.components.atoms.OverlayBoxWithImage
import com.example.ui.R
import com.example.ui.theme.Spacing2
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing6
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun PostTitleRow(item: IOffer) {
    Row(
        modifier = Modifier
            .padding(vertical = Spacing8)
            .padding(start = Spacing8, end = Spacing2)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.title,
            style = TextStyles.headingMedium,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(end = Spacing4)
        )
        if (item is PostItem) {
            OverlayBoxWithImage(
                overlayColor = MaterialTheme.colorScheme.background,
                imgResId = R.drawable.ic_offer,
                text = item.offers.size.toString() + " " + stringResource(R.string.offers),
                modifier = Modifier.padding(vertical = Spacing2, horizontal = Spacing6),
            )
        }
    }
}