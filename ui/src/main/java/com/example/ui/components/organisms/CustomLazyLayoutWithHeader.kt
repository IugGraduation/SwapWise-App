package com.example.ui.components.organisms

import androidx.compose.runtime.Composable
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.molecules.TopicsListHeader
import com.example.ui.models.TopicUiState

@Composable
fun CustomLazyLayoutWithHeader(
    topic: TopicUiState,
) {
    TopicsListHeader(topic = topic)

    CustomLazyLayout(topic)
}