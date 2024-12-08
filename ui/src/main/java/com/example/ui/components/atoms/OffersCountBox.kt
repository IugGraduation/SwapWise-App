package com.example.ui.components.atoms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.domain.models.PostItem
import com.example.ui.theme.Primary
import com.example.ui.theme.Spacing2
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing6
import com.example.ui.theme.TextStyles

@Composable
fun OffersCountBox(postItem: PostItem) {
    BoxRounded(color = MaterialTheme.colorScheme.background) {
        Row(
            modifier = Modifier.padding(vertical = Spacing2, horizontal = Spacing6),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_offer),
                contentDescription = "",
                modifier = Modifier.size(12.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            HorizontalSpacer(Spacing4)
            Text(
                postItem.offersCount.toString() + " " + stringResource(R.string.offers),
                style = TextStyles.captionSmall
            )
        }
    }
}