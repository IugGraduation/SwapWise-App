package com.example.ui.confirm_number

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ui.R
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.Header
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.templates.ScreenTemplate
import com.example.ui.home.navigateToHome
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.RadiusMedium
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing56
import com.example.ui.theme.Spacing72
import com.example.ui.theme.Spacing8
import com.example.ui.theme.Spacing80
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun OtpScreen(navController: NavController, viewModel: ConfirmNumberViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.data.shouldNavigateToHome) {
        if (state.data.shouldNavigateToHome) navController.navigateToHome {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
    }

    OtpContent(
        state = state,
        confirmNumberInteractions = viewModel
    )
}

@Composable
fun OtpContent(
    state: MyUiState<ConfirmNumberUiState>,
    confirmNumberInteractions: IConfirmNumberInteractions,
) {
    ScreenTemplate(baseUiState = state.baseUiState) {
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
                isDarkTheme = state.data.isDarkTheme
            )
            VerticalSpacer(Spacing8)
            Text(
                text = stringResource(R.string.an_4_digit_code_has_been_sent_to_your_phone_number),
                style = TextStyles.bodyLarge,
            )
            VerticalSpacer(Spacing24)
            SwapWiseOtpTextField(
                otp = state.data.otp,
                onOtpChange = confirmNumberInteractions::onOtpChange,
                isDarkTheme = state.data.isDarkTheme,
                otpLength = state.data.otpLength
            )
            VerticalSpacer(Spacing72)
            SwapWiseFilledButton(
                onClick = confirmNumberInteractions::onClickConfirm,
                text = stringResource(R.string.confirm),
                enabled = state.data.isConfirmButtonEnabled,
            )
        }
    }
}

@Composable
private fun SwapWiseOtpTextField(
    otp: String,
    onOtpChange: (String) -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    otpLength: Int = 4,
) {
    val otpChars = otp.toCharArray().toMutableList().apply {
        while (size < otpLength) {
            add(' ')
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        val backgroundColor = if (isDarkTheme) {
            MaterialTheme.color.onBackground
        } else {
            Tertiary
        }

        val focusRequesters = List(otpLength) { remember { FocusRequester() } }

        repeat(otpLength) { index ->

            Box(
                modifier = Modifier
                    .size(width = Spacing56, height = Spacing56)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(RadiusMedium),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                BasicTextField(
                    value = otpChars.getOrElse(index) { ' ' }.toString(),
                    onValueChange = { value ->
                        val newChar = value.trim()
                        if (newChar.isNotEmpty()) {
                            otpChars[index] = newChar.last()
                            onOtpChange(otpChars.joinToString(""))

                            if (index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                        else {
                            otpChars[index] = ' '
                            onOtpChange(otpChars.joinToString(""))
                            if (index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.headlineLarge.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequesters[index]),
                )
            }
        }
    }
}


//@Preview(showSystemUi = false, showBackground = true,)
@Composable
fun PreviewOtpContent() {
    GraduationProjectTheme {
        OtpContent(
            state = MyUiState(ConfirmNumberUiState()),
            confirmNumberInteractions = object : IConfirmNumberInteractions {
                override fun onOtpChange(newOtp: String) {}
                override fun onClickConfirm() {}
            }
        )
    }
}