package com.example.ui.otp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.R
import com.example.ui.components.atoms.CustomButton
import com.example.ui.components.atoms.CustomOtpTextField
import com.example.ui.components.atoms.Header
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.templates.PageTemplate
import com.example.ui.theme.BlackTertiary
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing40
import com.example.ui.theme.Spacing72
import com.example.ui.theme.Spacing8
import com.example.ui.theme.Spacing80
import com.example.ui.theme.TextStyles

@Composable
fun OTPPage(viewModel: OtpViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    PageTemplate {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing16)
        ) {
            VerticalSpacer(Spacing80)
            Image(
                painter = painterResource(R.drawable.image_otp),
                contentDescription = stringResource(R.string.image_otp),
                modifier = Modifier.fillMaxWidth()
            )
            VerticalSpacer(Spacing40)
            Header(title = stringResource(R.string.enter_otp))
            VerticalSpacer(Spacing8)
            Text(
                stringResource(R.string.an_4_digit_code_has_been_sent_to_your_phone_number),
                style = TextStyles.bodyLarge,
            )
            VerticalSpacer(Spacing24)
            CustomOtpTextField(
                otp = uiState.otp,
                onOtpChanged = viewModel::onOtpChange
            )
            VerticalSpacer(Spacing72)
            CustomButton(
                onClick = viewModel::onClickConfirm,
                text = stringResource(R.string.confirm),
                enabled = uiState.isConfirmButtonEnabled,
            )
        }
    }
}

//@Preview(showSystemUi = false, showBackground = true)
@Composable
fun PreviewOTPPage() {
    GraduationProjectTheme(darkTheme = false) {
        OTPPage()
    }
}