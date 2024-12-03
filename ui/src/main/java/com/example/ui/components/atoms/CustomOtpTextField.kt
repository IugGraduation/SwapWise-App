package com.example.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.RadiusMedium
import com.example.ui.theme.Spacing56
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TextStyles

@Composable
fun CustomOtpTextField(
    otp: String,
    onOtpChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    otpLength: Int = 4,
) {
    val otpChars = otp.toCharArray().toMutableList()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        val backgroundColor = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.onBackground
        }else{
            Tertiary
        }

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
                    onValueChange = { newChar ->
                        if (newChar.length == 1) {
                            otpChars[index] = newChar.first()
                            onOtpChanged(otpChars.joinToString(""))
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    textStyle = TextStyles.headingLarge.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCustomOtpTextField() {
    var otp by remember { mutableStateOf("123") }


    GraduationProjectTheme(darkTheme = false) {
        CustomOtpTextField(
            otp = otp,
            onOtpChanged = { newOtp -> otp = newOtp }
        )
    }
}
