package com.example.ui.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.LoginValidationUseCase
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.PasswordTextField
import com.example.ui.components.molecules.PhoneTextField
import com.example.ui.login.LoginUIState
import com.example.ui.login.LoginViewModel
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing8

@Composable
fun LoginForm(
    uiState: LoginUIState,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    togglePasswordVisibility: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PhoneTextField(uiState.phone, onPhoneChange, errorMessage = uiState.phoneError)
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            isPasswordVisible = uiState.isPasswordVisible,
            onVisibilityToggle = togglePasswordVisibility,
            errorMessage = uiState.passwordError
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