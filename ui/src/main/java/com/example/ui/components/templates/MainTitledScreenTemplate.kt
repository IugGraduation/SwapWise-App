package com.example.ui.components.templates

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.ui.base.BaseUiState
import com.example.ui.models.BottomBarUiState
import com.example.ui.theme.TextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTitledScreenTemplate(
    title: String,
    bottomBarState: BottomBarUiState,
    baseUiState: BaseUiState = BaseUiState(),
    content: @Composable () -> Unit
) {
    BottomBarTemplate(
        topBar = {
            TopAppBar(title = { Text(text = title, style = TextStyles.headingExtraLarge) },)
        },
        bottomBarState = bottomBarState,
        baseUiState = baseUiState,
    ) {
        content()
    }
}

