package com.example.ui.signup

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ui.R
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.Header
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.Footer
import com.example.ui.components.molecules.PasswordTextField
import com.example.ui.components.templates.ScreenTemplate
import com.example.ui.home.navigateToHome
import com.example.ui.login.navigateToLogin
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing56
import com.example.ui.theme.Spacing8
import com.example.ui.theme.color

@Composable
fun SignupScreen(
    navController: NavController,
    signupViewModel: SignupViewModel = hiltViewModel(),
) {
    val state by signupViewModel.state.collectAsState()

    LaunchedEffect(signupViewModel.effect) {
        signupViewModel.effect.collect { effect ->
            when (effect) {
                SignupEffects.NavigateToLogin -> navController.navigateToLogin {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }

                SignupEffects.NavigateToHome -> navController.navigateToHome()
            }
        }
    }

    SignupContent(
        state = state,
        signupInteractions = signupViewModel,
    )
}

@Composable
fun SignupContent(
    state: MyUiState<SignupUiState>,
    signupInteractions: ISignupInteractions,
) {
    ScreenTemplate(baseUiState = state.baseUiState,) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing16)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing24)
        ) {
            VerticalSpacer(Spacing56)
            Header(
                title = stringResource(R.string.sign_up),
                imgPainter = painterResource(R.drawable.img_signup),
                imgPainterDarkTheme = painterResource(R.drawable.img_signup_dark),
                imgContentDescription = stringResource(R.string.image_signup),
                isDarkTheme = state.data.isDarkTheme,
                modifier = Modifier.focusable()
            )
            SignupForm(
                state = state.data,
                signupInteractions = signupInteractions,
            )
            SwapWiseFilledButton(
                onClick = signupInteractions::onClickSignup,
                text = stringResource(R.string.signup)
            )
            Footer(
                footerText = stringResource(R.string.joined_us_before),
                buttonText = stringResource(R.string.login),
                onClickButton = signupInteractions::navigateToLogin
            )

        }
    }
}

@Composable
fun SignupForm(
    state: SignupUiState,
    signupInteractions: ISignupInteractions,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing8)
    ) {
        SwapWiseTextField(
            value = state.fullName,
            onValueChange = signupInteractions::onFullNameChange,
            placeholder = stringResource(R.string.full_name),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_user),
                    contentDescription = stringResource(R.string.full_name),
                    tint = MaterialTheme.color.textTertiary
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            errorMessage = state.signupError.fullNameError
        )
        SwapWiseTextField(
            value = state.phone,
            onValueChange = signupInteractions::onPhoneChange,
            placeholder = stringResource(R.string.phone_number),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_phone),
                    contentDescription = stringResource(R.string.phone_number),
                    tint = MaterialTheme.color.textTertiary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            errorMessage = state.signupError.phoneError
        )
        PasswordTextField(
            value = state.password,
            onValueChange = signupInteractions::onPasswordChange,
            isPasswordVisible = state.isPasswordVisible,
            onVisibilityToggle = signupInteractions::togglePasswordVisibility,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            errorMessage = state.signupError.passwordError
        )
        PasswordTextField(
            value = state.confirmPassword,
            onValueChange = signupInteractions::onConfirmPasswordChange,
            isPasswordVisible = state.isConfirmPasswordVisible,
            onVisibilityToggle = signupInteractions::toggleConfirmPasswordVisibility,
            placeholder = stringResource(R.string.confirm_password),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            errorMessage = state.signupError.confirmPasswordError
        )
        SwapWiseTextField(
            value = state.bestBarterSpot,
            onValueChange = signupInteractions::onBestBarterSpotChange,
            placeholder = stringResource(R.string.best_barter_spot),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = stringResource(R.string.best_barter_spot),
                    tint = MaterialTheme.color.textTertiary
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            errorMessage = state.signupError.bestBarterSpotError
        )
        SwapWiseTextField(
            value = state.bio,
            onValueChange = signupInteractions::onBioChange,
            placeholder = stringResource(R.string.bio),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_bio),
                    contentDescription = stringResource(R.string.bio),
                    tint = MaterialTheme.color.textTertiary
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Down) }),
        )
    }
}


@Preview(
    showSystemUi = false, showBackground = true,
    device = "spec:width=1080px,height=2790px,dpi=440",
)
@Composable
fun PreviewSignupContent() {
    GraduationProjectTheme {
        SignupContent(
            state = MyUiState(SignupUiState()),
            signupInteractions = object : ISignupInteractions {
                override fun onFullNameChange(newValue: String) {}
                override fun onPhoneChange(newValue: String) {}
                override fun onPasswordChange(newValue: String) {}
                override fun onConfirmPasswordChange(newValue: String) {}
                override fun onBestBarterSpotChange(newValue: String) {}
                override fun onBioChange(newValue: String) {}
                override fun togglePasswordVisibility() {}
                override fun toggleConfirmPasswordVisibility() {}
                override fun onClickSignup() {}
                override fun navigateToLogin() {}
            },
        )
    }
}