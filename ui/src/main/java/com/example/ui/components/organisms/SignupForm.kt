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
import com.example.ui.signup.SignupActions
import com.example.ui.signup.SignupUIState
import com.example.ui.signup.SignupViewModel
import com.example.ui.theme.Spacing8

@Composable
fun SignupForm(
    uiState: SignupUIState,
    actions: SignupActions,
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        FullNameTextField(
            uiState.fullName,
            actions.onFullNameChange,
            errorMessage = uiState.fullNameError
        )
        VerticalSpacer(height = Spacing8)
        PhoneTextField(
            uiState.phone,
            actions.onPhoneChange,
            errorMessage = uiState.phoneError
        )
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = uiState.password,
            onValueChange = actions.onPasswordChange,
            isPasswordVisible = uiState.isPasswordVisible,
            onVisibilityToggle = actions.togglePasswordVisibility,
            errorMessage = uiState.passwordError
        )
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = uiState.confirmPassword,
            onValueChange = actions.onConfirmPasswordChange,
            isPasswordVisible = uiState.isConfirmPasswordVisible,
            onVisibilityToggle = actions.toggleConfirmPasswordVisibility,
            placeholder = stringResource(R.string.confirm_password),
            errorMessage = uiState.confirmPasswordError
        )
        VerticalSpacer(height = Spacing8)
        BestBarterSpotTextField(
            uiState.bestBarterSpot,
            actions.onBestBarterSpotChange,
            errorMessage = uiState.bestBarterSpotError
        )
        VerticalSpacer(height = Spacing8)
        BioTextField(
            uiState.bio,
            actions.onBioChange,
            errorMessage = uiState.bioError
        )

    }
}

