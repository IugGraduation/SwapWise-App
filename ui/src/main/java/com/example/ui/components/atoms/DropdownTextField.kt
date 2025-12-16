package com.example.ui.components.atoms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun <T> DropdownTextField(
    modifier: Modifier = Modifier,
    selectedValue: T?,
    options: List<T>,
    onValueChange: (T) -> Unit,
    placeholder: String,
    errorMessage: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    valueToString: (T) -> String = { it.toString() }
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        SwapWiseTextField(
            value = selectedValue?.let(valueToString) ?: "",
            onValueChange = {},
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = {
                Icon(
                    imageVector = when (expanded) {
                        true -> Icons.Default.KeyboardArrowUp
                        else -> Icons.Default.ArrowDropDown
                    },
                    contentDescription = ""
                )
            },
            modifier = Modifier.clickable { expanded = !expanded },
            isEditable = false
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = valueToString(selectionOption)) },
                    onClick = {
                        onValueChange(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
    VerticalSpacer(Spacing16)

    AnimatedVisibility(!errorMessage.isNullOrEmpty()) {
        VerticalSpacer(Spacing8)
        Text(
            text = errorMessage ?: "",
            color = MaterialTheme.color.danger,
            style = TextStyles.captionMedium
        )
    }
}


@Preview(
    showSystemUi = false, showBackground = true,
    device = "spec:width=1080px,height=2790px,dpi=440",
)
@Composable
fun PreviewDropdownContent() {
    GraduationProjectTheme {
        DropdownTextField(
            selectedValue = null,
            options = listOf("Gaza"),
            onValueChange = {},
            placeholder = "Select location"
        )
    }
}