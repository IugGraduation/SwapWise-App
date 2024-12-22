package com.example.ui.components.molecules

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.theme.TextStyles


@Composable
fun SimpleCustomMultilineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
) {
    CustomTextFieldWithErrorMsg(errorMessage = errorMessage) {
        CustomMultilineTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyles.hint,
                )
            },
            leadingIcon = leadingIcon,
            modifier = modifier
        )
    }
}
