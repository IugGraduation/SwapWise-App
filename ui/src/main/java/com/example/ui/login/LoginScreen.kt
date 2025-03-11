package com.example.ui.login

import androidx.compose.foundation.background
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
import com.example.ui.otp.navigateToOtp
import com.example.ui.signup.navigateToSignup
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing56
import com.example.ui.theme.Spacing8
import com.example.ui.theme.color

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val state by loginViewModel.state.collectAsState()

    LaunchedEffect(loginViewModel.effect) {
        loginViewModel.effect.collect { effect ->
            when (effect) {
                is LoginEffects.NavigateToHome -> navController.navigateToHome {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }

                is LoginEffects.NavigateToSignup -> navController.navigateToSignup {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }

                LoginEffects.NavigateToOtp -> navController.navigateToOtp()
            }
        }
    }

    LoginContent(
        state = state,
        loginInteractions = loginViewModel,
    )
}


@Composable
fun LoginContent(
    state: MyUiState<LoginUiState>,
    loginInteractions: ILoginInteractions,
) {
    ScreenTemplate(baseUiState = state.baseUiState,) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing16)
                .background(color = MaterialTheme.color.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing24)
        ) {
            VerticalSpacer(Spacing56)

            Header(
                title = stringResource(R.string.login),
                imgPainter = painterResource(R.drawable.img_login),
                imgPainterDarkTheme = painterResource(R.drawable.img_login_dark),
                imgContentDescription = stringResource(R.string.image_login),
                isDarkTheme = state.data.isDarkTheme,
                modifier = Modifier.focusable()
            )
            LoginForm(
                state = state.data,
                onPhoneChange = loginInteractions::onPhoneChange,
                onPasswordChange = loginInteractions::onPasswordChange,
                togglePasswordVisibility = loginInteractions::togglePasswordVisibility,
            )
            SwapWiseFilledButton(
                onClick = loginInteractions::onClickLogin,
                text = stringResource(R.string.login)
            )
            Footer(
                footerText = stringResource(R.string.don_t_have_an_account),
                buttonText = stringResource(R.string.sign_up),
                onClickButton = loginInteractions::NavigateToSignup
            )
        }
    }
}

@Composable
private fun LoginForm(
    state: LoginUiState,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    togglePasswordVisibility: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Spacing8)) {
        SwapWiseTextField(
            value = state.phone,
            onValueChange = onPhoneChange,
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
            errorMessage = state.loginError.phoneError
        )
        PasswordTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            isPasswordVisible = state.isPasswordVisible,
            onVisibilityToggle = togglePasswordVisibility,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Down) }),
            errorMessage = state.loginError.passwordError
        )
    }
}


@Preview
@Composable
fun PreviewLoginContent() {
    GraduationProjectTheme {
        LoginContent(
            state = MyUiState(LoginUiState()),
            loginInteractions = object : ILoginInteractions {
                override fun onPhoneChange(newValue: String) {}
                override fun onPasswordChange(newValue: String) {}
                override fun onClickLogin() {}
                override fun togglePasswordVisibility() {}
                override fun NavigateToSignup() {}
            },
        )
    }
}