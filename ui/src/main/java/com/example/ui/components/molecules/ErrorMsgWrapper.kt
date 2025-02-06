package com.example.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.ui.R
import com.example.ui.components.atoms.CustomTextField
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.components.atoms.ErrorText
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8

@Composable
fun ErrorMsgWrapper(
    errorMessage: String? = null,
    content: @Composable () -> Unit
) {
    Column {
        content()

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

        ErrorMsgWrapper {
            CustomTextField(
                value = textState, onValueChange = { textState = it }, placeholder = "User",
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_user), // Use a vector icon or painter
                        contentDescription = "User",
                        modifier = Modifier.padding(vertical = Spacing16)
                    )
                }
            )
        }
    }
}