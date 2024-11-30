package com.example.ui.components.molecules

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
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
import com.example.ui.components.atoms.CustomTextField
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.theme.GraduationProjectTheme

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityToggle: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder:String = stringResource(R.string.password),
) {
    CustomTextField(
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
            CustomTextFieldIcon(
                painter = painterResource(R.drawable.image_password_lock),
                contentDescription = stringResource(R.string.password),
            )
        },
        trailingIcon = {
            IconButton(onClick = { onVisibilityToggle() }) {
                CustomTextFieldIcon(
                    painter = painterResource(
                        if (isPasswordVisible) R.drawable.image_eye_closed
                        else R.drawable.image_eye
                    ),
                    contentDescription = if (isPasswordVisible) {
                        stringResource(R.string.hide_password)
                    } else {
                        stringResource(R.string.show_password)
                    },
                )
            }
        }
    )
}


//@Preview(showSystemUi = true)
@Composable
fun PreviewPasswordTextField() {
    GraduationProjectTheme {
        var textState by remember { mutableStateOf("") }
        var isPasswordVisible by  remember { mutableStateOf(false) }

        PasswordTextField(textState, { textState = it }, isPasswordVisible, { isPasswordVisible = !isPasswordVisible})
    }
}