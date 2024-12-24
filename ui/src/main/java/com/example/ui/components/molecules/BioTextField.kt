package com.example.ui.components.molecules

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.ui.R
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing4
import com.example.ui.theme.TextStyles


@Composable
fun BioTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    CustomTextFieldWithErrorMsg(errorMessage = errorMessage) {
        CustomMultilineTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Row {
                    Text(
                        text = stringResource(R.string.bio),
                        style = TextStyles.hint,
                    )
                    HorizontalSpacer(Spacing4)
                    Text(
                        text = stringResource(R.string.optional),
                        style = TextStyles.hint,
                        color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                    )

                }
            },
            leadingIcon = {
                CustomTextFieldIcon(
                    painter = painterResource(R.drawable.ic_bio)
                )
            },
            modifier = modifier
        )
    }
}



//@Preview(showSystemUi = true, showBackground = true,)
@Composable
fun PreviewBioTextField() {
    GraduationProjectTheme {
        var textState by remember { mutableStateOf("") }

        BioTextField(textState, { textState = it })
    }
}