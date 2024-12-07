package com.example.ui.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
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
import com.example.ui.signup.SignupUiState
import com.example.ui.theme.Spacing8

@Composable
fun SignupForm(
    state: SignupUiState,
    actions: SignupActions,
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        FullNameTextField(
            state.fullName,
            actions.onFullNameChange,
            errorMessage = state.fullNameError
        )
        VerticalSpacer(height = Spacing8)
        PhoneTextField(
            state.phone,
            actions.onPhoneChange,
            errorMessage = state.phoneError
        )
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = state.password,
            onValueChange = actions.onPasswordChange,
            isPasswordVisible = state.isPasswordVisible,
            onVisibilityToggle = actions.togglePasswordVisibility,
            errorMessage = state.passwordError
        )
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = state.confirmPassword,
            onValueChange = actions.onConfirmPasswordChange,
            isPasswordVisible = state.isConfirmPasswordVisible,
            onVisibilityToggle = actions.toggleConfirmPasswordVisibility,
            placeholder = stringResource(R.string.confirm_password),
            errorMessage = state.confirmPasswordError
        )
        VerticalSpacer(height = Spacing8)
        BestBarterSpotTextField(
            state.bestBarterSpot,
            actions.onBestBarterSpotChange,
            errorMessage = state.bestBarterSpotError
        )
        VerticalSpacer(height = Spacing8)
        BioTextField(
            state.bio,
            actions.onBioChange,
            errorMessage = state.bioError
        )

    }
}

