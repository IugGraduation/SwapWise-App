package com.example.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.components.atoms.CustomButton
import com.example.ui.components.atoms.Header
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.Footer
import com.example.ui.components.organisms.SignupForm
import com.example.ui.components.templates.ScreenTemplate
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing80


@Composable
fun SignupContent(
    state: SignupUiState,
    actions: SignupActions,
) {
    ScreenTemplate {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing16)
                .verticalScroll(rememberScrollState())
        ) {
            VerticalSpacer(Spacing80)
            Header(
                title = stringResource(R.string.sign_up),
                imgPainter = painterResource(R.drawable.img_signup),
                imgPainterDarkTheme = painterResource(R.drawable.img_signup_dark),
                imgContentDescription = stringResource(R.string.image_signup),
            )
            VerticalSpacer(Spacing24)
            SignupForm(
                state = state,
                actions = actions,
            )
            VerticalSpacer(Spacing24)
            CustomButton(
                onClick = actions.onClickSignup,
                text = stringResource(R.string.signup)
            )
            Footer(
                footerText = stringResource(R.string.joined_us_before),
                buttonText = stringResource(R.string.login),
                onClickButton = actions.onClickGoToLogin
            )

        }
    }
}


//@Preview(
//    showSystemUi = false, showBackground = true,
//    device = "spec:width=1080px,height=2790px,dpi=440",
//)
@Composable
fun PreviewSignupContent() {
    GraduationProjectTheme {
        SignupContent(
            state = SignupUiState(),
            actions = SignupActions(
                onFullNameChange = { },
                onPhoneChange = { },
                onPasswordChange = { },
                onConfirmPasswordChange = { },
                onBestBarterSpotChange = { },
                onBioChange = { },
                togglePasswordVisibility = { },
                toggleConfirmPasswordVisibility = { },
                onClickSignup = { },
                onClickGoToLogin = { }
            )
        )
    }
}