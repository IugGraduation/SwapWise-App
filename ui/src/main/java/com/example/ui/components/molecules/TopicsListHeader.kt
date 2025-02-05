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
import com.example.ui.R
import com.example.ui.components.atoms.CustomTextButton
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun TopicsListHeader(
    title: String,
    onClickSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = Spacing16).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = TextStyles.headingMedium,
            color = MaterialTheme.colorScheme.primary
        )
        CustomTextButton(
            onClick = onClickSeeAll,
            text = stringResource(R.string.see_all),
        )
    }
    VerticalSpacer(Spacing8)
}