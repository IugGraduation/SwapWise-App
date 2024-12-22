package com.example.ui.components.molecules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.components.atoms.CustomTextField
import com.example.ui.theme.GraduationProjectTheme

@Composable
fun SimpleCustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
) {
    CustomTextFieldWithErrorMsg(errorMessage = errorMessage) {
        CustomTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
        )
    }
}


//@Preview(showSystemUi = true)
@Composable
fun PreviewSimpleCustomTextField() {
    GraduationProjectTheme {
        var textState by remember { mutableStateOf("") }

        SimpleCustomTextField(textState, { textState = it })
    }
}