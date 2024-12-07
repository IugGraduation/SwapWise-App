package com.example.ui.confirm_number

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.components.atoms.CustomButton
import com.example.ui.components.atoms.CustomOtpTextField
import com.example.ui.components.atoms.Header
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.templates.PageTemplate
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing72
import com.example.ui.theme.Spacing8
import com.example.ui.theme.Spacing80
import com.example.ui.theme.TextStyles


@Composable
fun OtpContent(
    state: ConfirmNumberUiState,
    onOtpChange: (String) -> Unit,
    onClickConfirm: () -> Unit
) {
    PageTemplate {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing16)
        ) {
            VerticalSpacer(Spacing80)
            Header(
                title = stringResource(R.string.enter_otp),
                imgPainter = painterResource(R.drawable.img_otp),
                imgPainterDarkTheme = painterResource(R.drawable.img_otp_dark),
                imgContentDescription = stringResource(R.string.image_otp),
            )
            VerticalSpacer(Spacing8)
            Text(
                stringResource(R.string.an_4_digit_code_has_been_sent_to_your_phone_number),
                style = TextStyles.bodyLarge,
            )
            VerticalSpacer(Spacing24)
            CustomOtpTextField(
                otp = state.otp,
                onOtpChanged = onOtpChange,
                otpLength = state.otpLength
            )
            VerticalSpacer(Spacing72)
            CustomButton(
                onClick = onClickConfirm,
                text = stringResource(R.string.confirm),
                enabled = state.isConfirmButtonEnabled,
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = true,)
@Composable
fun PreviewOtpContent() {
    GraduationProjectTheme {
        OtpContent(
            state = ConfirmNumberUiState(),
            onOtpChange = { },
            onClickConfirm = { }
        )
    }
}