package com.example.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import com.example.ui.R
import com.example.ui.components.atoms.CustomTextField
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.components.atoms.ErrorText
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8

@Composable
fun CustomTextFieldWithErrorMsg(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    minLines: Int = 1,
    errorMessage: String? = null,
) {
    Column {
        CustomTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            visualTransformation = visualTransformation,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            modifier = modifier,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            minLines = minLines,
        )

        if (!errorMessage.isNullOrEmpty()) {
            VerticalSpacer(Spacing8)
            ErrorText(
                text = errorMessage,
            )
        }
    }
}


//@Preview(showSystemUi = true)
@Composable
fun PreviewCustomTextFieldWithErrorMsg() {
    GraduationProjectTheme {
        var textState by remember { mutableStateOf("") }

        CustomTextFieldWithErrorMsg(textState, { textState = it }, "User",
            leadingIcon = {
                CustomTextFieldIcon(
                    painter = painterResource(R.drawable.ic_user_name), // Use a vector icon or painter
                    contentDescription = "User",
                    modifier = Modifier.padding(vertical = Spacing16)
                )
            })
    }
}