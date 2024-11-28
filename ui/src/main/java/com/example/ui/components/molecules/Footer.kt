package com.example.ui.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.components.atoms.CustomTextButton
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.theme.BlackTertiary
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing40
import com.example.ui.theme.typography

@Composable
fun Footer(footerText: String, buttonText: String, onClickButton: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing16)
    ) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = Spacing40),
    ) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(footerText, style = typography.hint)
            HorizontalSpacer(Spacing4)
            CustomTextButton(onClick = onClickButton, text = buttonText)
        }
    }}
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewFooter() {
    Footer(
        footerText = stringResource(R.string.joined_us_before),
        buttonText = stringResource(R.string.login),
        onClickButton = {}
    )
}