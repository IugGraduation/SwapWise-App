package com.example.ui.components.atoms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.models.AsyncState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.IconSize24
import com.example.ui.theme.color

@Composable
fun <T> DropdownTextField(
    modifier: Modifier = Modifier,
    state: AsyncState<List<T>>,
    selectedItem: T? = null,
    onValueChange: (T) -> Unit,
    onRetry: () -> Unit = {},
    placeholder: String,
    errorMessage: String? = null,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    valueToString: (T) -> String = { it.toString() }
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        SwapWiseTextField(
            value = selectedItem?.let(valueToString) ?: "",
            onValueChange = {},
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = {
                when (state) {
                    is AsyncState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(IconSize24),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.color.primary
                        )
                    }

                    is AsyncState.Error -> {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.retry),
                            tint = MaterialTheme.color.danger,
                            modifier = Modifier.clickable { onRetry() }
                        )
                    }

                    else -> {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = MaterialTheme.color.textTertiary
                        )
                    }
                }
            },
            modifier = modifier.clickable(enabled = enabled && state is AsyncState.Success) {
                expanded = !expanded
            },
            isEditable = false,
            enabled = enabled,
            errorMessage = errorMessage ?: (state as? AsyncState.Error)?.message
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            state.data?.forEach { selectionOption ->
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
}

@Preview(showBackground = true)
@Composable
fun PreviewDropdownContent() {
    GraduationProjectTheme {
        DropdownTextField(
            state = AsyncState.Success(data = listOf("Gaza")),
            onValueChange = {},
            placeholder = "Select location"
        )
    }
}
