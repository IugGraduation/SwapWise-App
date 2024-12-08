package com.example.ui.components.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8

@Composable
fun CustomLazyRow(content: LazyListScope.() -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Spacing16),
        horizontalArrangement = Arrangement.spacedBy(Spacing8)
    ) {
        content()
    }
}

