package com.example.ui.components.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8

@Composable
fun CustomLazyColumn(content: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing16),
        verticalArrangement = Arrangement.spacedBy(Spacing8)
    ) {
        content()
    }
}