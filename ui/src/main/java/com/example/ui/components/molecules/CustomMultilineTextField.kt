package com.example.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.IconSizeMedium
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing12
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing32
import com.example.ui.theme.Spacing8
import com.example.ui.theme.Spacing80


@Composable
fun CustomMultilineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit),
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = Spacing80 + Spacing32)
            .background(
                MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(RadiusLarge)
            )
            .padding(horizontal = Spacing12, vertical = Spacing16),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Leading Icon
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier
                        .padding(end = Spacing16)
                        .size(IconSizeMedium),
                    contentAlignment = Alignment.Center,
                ) {
                    leadingIcon()
                }
            }

            // Text Input Field
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        placeholder()
                    }
                    innerTextField()
                }
            )
        }
    }
}

