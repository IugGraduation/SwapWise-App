package com.example.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ui.login.navigateToLogin

@Composable
fun SignupScreen(
    navController: NavController,
    signupViewModel: SignupViewModel = hiltViewModel(),
) {
    val uiState by signupViewModel.uiState.collectAsState()
    val actions = SignupActions(
        onFullNameChange = signupViewModel::onFullNameChange,
        onPhoneChange = signupViewModel::onPhoneChange,
        onPasswordChange = signupViewModel::onPasswordChange,
        onConfirmPasswordChange = signupViewModel::onConfirmPasswordChange,
        onBestBarterSpotChange = signupViewModel::onBestBarterSpotChange,
        onBioChange = signupViewModel::onBioChange,
        togglePasswordVisibility = signupViewModel::togglePasswordVisibility,
        toggleConfirmPasswordVisibility = signupViewModel::toggleConfirmPasswordVisibility,
        onClickSignup = signupViewModel::onClickSignup,
        onClickGoToLogin = { navController.navigateToLogin() }
    )

    SignupContent(
        uiState = uiState,
        actions = actions
    )

}

