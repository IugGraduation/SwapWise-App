package com.example.ui.signup

import android.content.res.Configuration
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
import com.example.domain.SignupValidationUseCase
import com.example.domain.ValidatePasswordUseCase
import com.example.domain.ValidatePhoneNumberUseCase
import com.example.ui.R
import com.example.ui.components.atoms.CustomButton
import com.example.ui.components.atoms.Header
import com.example.ui.components.atoms.HeaderImage
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.Footer
import com.example.ui.components.organisms.SignupForm
import com.example.ui.components.templates.PageTemplate
import com.example.ui.shared.SharedAuthViewModel
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing80

@Composable
fun SignupPage(
    signupViewModel: SignupViewModel = hiltViewModel(),
    sharedAuthViewModel: SharedAuthViewModel = hiltViewModel()
) {
    val uiState by signupViewModel.uiState.collectAsState()

    PageTemplate {
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
                contentDescription = stringResource(R.string.image_signup),
            )
            VerticalSpacer(Spacing24)
            SignupForm(
                uiState = uiState,
                signupViewModel = signupViewModel,
                sharedAuthViewModel = sharedAuthViewModel,
            )
            VerticalSpacer(Spacing24)
            CustomButton(
                onClick = signupViewModel::onClickSignup,
                text = stringResource(R.string.signup)
            )
            Footer(
                footerText = stringResource(R.string.joined_us_before),
                buttonText = stringResource(R.string.login),
                onClickButton = {}
            )

        }
    }
}

//@Preview(
//    showSystemUi = false, showBackground = true,
//    device = "spec:width=1080px,height=2790px,dpi=440",
//)
@Composable
fun PreviewSignupPage() {
    val signupViewModel = SignupViewModel(
        SignupValidationUseCase(
            ValidatePhoneNumberUseCase { "" },
            ValidatePasswordUseCase { "" }
        )
    )
    GraduationProjectTheme {
        SignupPage(signupViewModel)
    }
}