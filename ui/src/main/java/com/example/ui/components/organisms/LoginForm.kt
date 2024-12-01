package com.example.ui.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.PasswordTextField
import com.example.ui.components.molecules.PhoneTextField
import com.example.ui.login.LoginUIState
import com.example.ui.shared.SharedAuthViewModel
import com.example.ui.signup.LoginViewModel
import com.example.ui.theme.Spacing8

@Composable
fun LoginForm(
    uiState: LoginUIState,
    loginViewModel: LoginViewModel,
    sharedAuthViewModel: SharedAuthViewModel,
) {
    val isPasswordVisible by sharedAuthViewModel.isPasswordVisible.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        PhoneTextField(uiState.phone, loginViewModel::onPhoneChange, errorMessage = uiState.phoneError)
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = uiState.password,
            onValueChange = loginViewModel::onPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onVisibilityToggle = sharedAuthViewModel::togglePasswordVisibility,
            errorMessage = uiState.passwordError
        )
    }
}

