package com.example.ui.components.atoms

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.theme.BlackTertiary
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing56
import com.example.ui.theme.TextStyles

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    minLines: Int = 1,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyles.hint
            )
        },
        visualTransformation = visualTransformation,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp)
            .defaultMinSize(minHeight = Spacing56),
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        minLines = minLines,

        shape = RoundedCornerShape(RadiusLarge),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent, // No border when focused
            unfocusedIndicatorColor = Color.Transparent, // No border when not focused
            disabledIndicatorColor = Color.Transparent, // No border when disabled
            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}


//@Preview(showSystemUi = true)
@Composable
fun PreviewEditText() {
    GraduationProjectTheme {
        var textState by remember { mutableStateOf("") }

        CustomTextField(textState, { textState = it }, "User",
            leadingIcon = {
                CustomTextFieldIcon(
                    painter = painterResource(R.drawable.image_user_name), // Use a vector icon or painter
                    contentDescription = "User",
                    modifier = Modifier.padding(vertical = Spacing16)
                )
            })
    }
}