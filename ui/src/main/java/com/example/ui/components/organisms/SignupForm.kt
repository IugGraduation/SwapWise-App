package com.example.ui.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.ui.R
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.BestBarterSpotTextField
import com.example.ui.components.molecules.BioTextField
import com.example.ui.components.molecules.FullNameTextField
import com.example.ui.components.molecules.PasswordTextField
import com.example.ui.components.molecules.PhoneTextField
import com.example.ui.shared.SharedAuthViewModel
import com.example.ui.signup.SignupUIState
import com.example.ui.signup.SignupViewModel
import com.example.ui.theme.Spacing8

@Composable
fun SignupForm(
    uiState: SignupUIState,
    sharedAuthViewModel: SharedAuthViewModel,
    signupViewModel: SignupViewModel,
) {
    val isPasswordVisible by sharedAuthViewModel.isPasswordVisible.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        FullNameTextField(uiState.fullName, signupViewModel::onFullNameChange)
        VerticalSpacer(height = Spacing8)
        PhoneTextField(uiState.phone, signupViewModel::onPhoneChange)
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = uiState.password,
            onValueChange = signupViewModel::onPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onVisibilityToggle = sharedAuthViewModel::togglePasswordVisibility
        )
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = uiState.confirmPassword,
            onValueChange = signupViewModel::onConfirmPasswordChange,
            isPasswordVisible = uiState.isConfirmPasswordVisible,
            onVisibilityToggle = signupViewModel::toggleConfirmPasswordVisibility,
            placeholder = stringResource(R.string.confirm_password),
        )
        VerticalSpacer(height = Spacing8)
        BestBarterSpotTextField(uiState.bestBarterSpot, signupViewModel::onBestBarterSpotChange)
        VerticalSpacer(height = Spacing8)
        BioTextField(uiState.bio, signupViewModel::onBioChange)

    }
}

