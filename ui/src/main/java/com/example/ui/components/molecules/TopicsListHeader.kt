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
import com.example.ui.models.TopicUiState
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.util.getName

@Composable
fun TopicsListHeader(
    topic: TopicUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = Spacing16).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = topic.type.getName(),
            style = TextStyles.headingMedium,
            color = MaterialTheme.colorScheme.primary
        )
        CustomTextButton(
            onClick = topic.onClickSeeAll,
            text = stringResource(R.string.see_all),
        )
    }
    VerticalSpacer(Spacing8)
}