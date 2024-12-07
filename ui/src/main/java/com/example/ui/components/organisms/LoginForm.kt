package com.example.ui.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.PasswordTextField
import com.example.ui.components.molecules.PhoneTextField
import com.example.ui.login.LoginUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing8

@Composable
fun LoginForm(
    state: LoginUiState,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    togglePasswordVisibility: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PhoneTextField(state.phone, onPhoneChange, errorMessage = state.phoneError)
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            isPasswordVisible = state.isPasswordVisible,
            onVisibilityToggle = togglePasswordVisibility,
            errorMessage = state.passwordError
        )
    }
}


//@Preview(showSystemUi = false, showBackground = true)
@Composable
fun PreviewLoginForm() {
    GraduationProjectTheme {
//        LoginForm(LoginUIState(), LoginViewModel(), SharedAuthViewModel())
    }
}