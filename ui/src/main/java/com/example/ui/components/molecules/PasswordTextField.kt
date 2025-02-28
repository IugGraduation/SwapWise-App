package com.example.ui.components.molecules

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.ui.R
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.color

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityToggle: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.password),
    errorMessage: String? = null,
) {
    SwapWiseTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_password_lock),
                contentDescription = stringResource(R.string.password),
                tint = MaterialTheme.color.textTertiary
            )
        },
        trailingIcon = {
            IconButton(onClick = { onVisibilityToggle() }) {
                Icon(
                    painter = painterResource(
                        if (isPasswordVisible) R.drawable.ic_eye
                        else R.drawable.ic_eye_closed
                    ),
                    contentDescription = if (isPasswordVisible) {
                        stringResource(R.string.hide_password)
                    } else {
                        stringResource(R.string.show_password)
                    },
                    tint = MaterialTheme.color.textTertiary
                )
            }
        },
        errorMessage = errorMessage
    )
}


//@Preview(showSystemUi = true)
@Composable
fun PreviewPasswordTextField() {
    GraduationProjectTheme {
        var textState by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        PasswordTextField(
            textState,
            { textState = it },
            isPasswordVisible,
            { isPasswordVisible = !isPasswordVisible })
    }
}