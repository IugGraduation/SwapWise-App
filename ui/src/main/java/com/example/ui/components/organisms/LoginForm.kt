package com.example.ui.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.PasswordTextField
import com.example.ui.components.molecules.PhoneTextField
import com.example.ui.shared.SharedAuthViewModel
import com.example.ui.theme.Spacing8

@Composable
fun LoginForm(
    sharedAuthViewModel: SharedAuthViewModel = hiltViewModel(),
    phone: String,
    onPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    val isPasswordVisible by sharedAuthViewModel.isPasswordVisible.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        PhoneTextField(phone, onPhoneChange)
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onVisibilityToggle = sharedAuthViewModel::togglePasswordVisibility
        )
    }
}

