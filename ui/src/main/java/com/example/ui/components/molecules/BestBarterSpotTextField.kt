package com.example.ui.components.molecules

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
import com.example.ui.theme.GraduationProjectTheme

@Composable
fun BestBarterSpotTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    CustomTextFieldWithErrorMsg(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        errorMessage = errorMessage,
        placeholder = stringResource(R.string.best_barter_spot),
        leadingIcon = {
            CustomTextFieldIcon(
                painter = painterResource(R.drawable.image_best_barter_spot),
                contentDescription = stringResource(R.string.best_barter_spot),
            )
        },
    )
}


//@Preview(showSystemUi = true)
@Composable
fun PreviewBestSpotTextField() {
    GraduationProjectTheme {
        var textState by remember { mutableStateOf("") }

        BestBarterSpotTextField(textState, { textState = it })
    }
}