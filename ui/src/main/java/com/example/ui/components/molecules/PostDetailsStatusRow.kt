package com.example.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.components.atoms.PostDetailsStatusItem
import com.example.ui.theme.BlackFourth
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing24

@Composable
fun PostDetailsStatusRow(rate: Float, offersCount: Int, isOpen: Boolean) {
    Row(
        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PostDetailsStatusItem(title = stringResource(R.string.rate), value = rate.toString())
        HorizontalSpacer(Spacing24)
        Spacer(
            modifier = androidx.compose.ui.Modifier
                .size(width = 1.5.dp, height = 20.dp)
                .background(color = BlackFourth, shape = RoundedCornerShape(RadiusLarge))
        )
        HorizontalSpacer(Spacing24)
        PostDetailsStatusItem(title = stringResource(R.string.offers), value = offersCount.toString())
        HorizontalSpacer(Spacing24)
        Spacer(
            modifier = androidx.compose.ui.Modifier
                .size(width = 1.5.dp, height = 20.dp)
                .background(color = BlackFourth, shape = RoundedCornerShape(RadiusLarge))
        )
        HorizontalSpacer(Spacing24)
        val state = if(isOpen) stringResource(R.string.open) else stringResource(R.string.closed)
        PostDetailsStatusItem(title = stringResource(R.string.state), value = state)
    }
}