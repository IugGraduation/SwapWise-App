package com.example.ui.components.organisms

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.TopicsListHeader
import com.example.ui.models.TopicUiState
import com.example.ui.models.getName
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8

@Composable
fun CustomLazyLayoutWithHeader(
    topic: TopicUiState,
) {
    TopicsListHeader(
        text = topic.type.getName(),
        onClickSeeAll = topic.onClickSeeAll,
        modifier = Modifier.padding(horizontal = Spacing16)
    )
    VerticalSpacer(Spacing8)

    CustomLazyLayout(topic)
}