package com.example.ui.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.R
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.BestBarterSpotTextField
import com.example.ui.components.molecules.BioTextField
import com.example.ui.components.molecules.FullNameTextField
import com.example.ui.components.molecules.PasswordTextField
import com.example.ui.components.molecules.PhoneTextField
import com.example.ui.shared.SharedAuthViewModel
import com.example.ui.signup.SignupViewModel
import com.example.ui.theme.Spacing8

@Composable
fun SignupForm(
    sharedAuthViewModel: SharedAuthViewModel = hiltViewModel(),
    signupViewModel: SignupViewModel = hiltViewModel(),
    fullName: String,
    onFullNameChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    bestBarterSpot: String,
    onBestBarterSpotChange: (String) -> Unit,
    bio: String,
    onBioChange: (String) -> Unit,
) {
    val isPasswordVisible by sharedAuthViewModel.isPasswordVisible.collectAsState()
    val signupUIState by signupViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        FullNameTextField(fullName, onFullNameChange)
        VerticalSpacer(height = Spacing8)
        PhoneTextField(phone, onPhoneChange)
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onVisibilityToggle = sharedAuthViewModel::togglePasswordVisibility
        )
        VerticalSpacer(height = Spacing8)
        PasswordTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            isPasswordVisible = signupUIState.isConfirmPasswordVisible,
            onVisibilityToggle = signupViewModel::toggleConfirmPasswordVisibility,
            placeholder = stringResource(R.string.confirm_password),
        )
        VerticalSpacer(height = Spacing8)
        BestBarterSpotTextField(bestBarterSpot, onBestBarterSpotChange)
        VerticalSpacer(height = Spacing8)
        BioTextField(bio, onBioChange)

    }
}

