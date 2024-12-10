package com.example.ui.components.atoms

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ui.R
import com.example.ui.theme.Correct
import com.example.ui.theme.Danger
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8

@Composable
fun TextStatus(isOpen: Boolean, modifier: Modifier = Modifier) {
    val text = if (isOpen) stringResource(R.string.open) else stringResource(R.string.closed)
    val color = if (isOpen) Correct else Danger
    Text(
        text = text,
        modifier = modifier.padding(horizontal = Spacing8, vertical = Spacing4),
        color = color,
        fontSize = 8.sp,
        lineHeight = 8.sp,
        fontWeight = FontWeight.SemiBold
    )
}