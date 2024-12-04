package com.example.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.R
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.IconSizeMedium
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing32
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.Spacing80
import com.example.ui.theme.TextStyles


@Composable
fun BioTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    CustomTextFieldWithErrorMsg(errorMessage = errorMessage) {
        CustomBioTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
        )
    }
}

@Composable
private fun CustomBioTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = Spacing80 + Spacing32)
            .background(
                MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(RadiusLarge)
            )
            .padding(horizontal = Spacing8, vertical = Spacing16),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Leading Icon
            Box(
                modifier = Modifier
                    .padding(end = Spacing8)
                    .size(IconSizeMedium),
                contentAlignment = Alignment.Center
            ) {
                CustomTextFieldIcon(
                    painter = painterResource(R.drawable.ic_bio),
                    contentDescription = stringResource(R.string.bio),
                )
            }

            // Text Input Field
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
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
                    }
                    innerTextField()
                }
            )
        }
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