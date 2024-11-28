package com.example.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.R
import com.example.ui.components.atoms.CustomButton
import com.example.ui.components.molecules.Footer
import com.example.ui.components.atoms.Header
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.organisms.LoginForm
import com.example.ui.signup.LoginViewModel
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing80

@Composable
fun LoginPage(viewModel: LoginViewModel = hiltViewModel()) {
    val phone by viewModel.phone.collectAsState()
    val password by viewModel.password.collectAsState()

    GraduationProjectTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing16)
        ) {
            VerticalSpacer(Spacing80)
            Image(
                painter = painterResource(R.drawable.image_login),
                contentDescription = stringResource(R.string.image_login),
                modifier = Modifier.fillMaxWidth()
            )
            VerticalSpacer(Spacing24)
            Header(title = stringResource(R.string.login))
            VerticalSpacer(Spacing24)
            LoginForm(
                phone = phone,
                onPhoneChange = viewModel::onPhoneChange,
                password = password,
                onPasswordChange = viewModel::onPasswordChange,
            )
            VerticalSpacer(Spacing24)
            CustomButton(
                onClick = viewModel::onClickLogin,
                text = stringResource(R.string.login)
            )
            Footer(
                footerText = stringResource(R.string.don_t_have_an_account),
                buttonText = stringResource(R.string.sign_up),
                onClickButton = {}
            )

        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun PreviewLoginPage() {
    GraduationProjectTheme {
        LoginPage()
    }
}