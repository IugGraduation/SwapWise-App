package com.example.ui.components.atoms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.example.ui.theme.IconSizeMedium
import com.example.ui.theme.MultiLineEditTextHeight
import com.example.ui.theme.OneLineEditTextHeight
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing12
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun SwapWiseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    placeholderComposable: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessage: String? = null,
    isMultiline: Boolean = false,
    minHeight: Dp = if (isMultiline) MultiLineEditTextHeight else OneLineEditTextHeight,
    isEditable: Boolean = true
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
                .background(
                    MaterialTheme.color.onBackground,
                    shape = RoundedCornerShape(RadiusLarge)
                )
                .padding(horizontal = Spacing12, vertical = Spacing16)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                leadingIcon?.let {
                    Box(
                        modifier = Modifier
                            .padding(end = Spacing8)
                            .size(IconSizeMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        it()
                    }
                }

                BasicTextField(
                    value = value,
                    enabled = isEditable,
                    onValueChange = onValueChange,
                    textStyle = TextStyles.hint.copy(
                        color = if(isEditable) MaterialTheme.color.textPrimary else MaterialTheme.color.textTertiary
                    ),
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    visualTransformation = visualTransformation,
                    modifier = Modifier.weight(1f),
                    cursorBrush = SolidColor(MaterialTheme.color.textPrimary),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            placeholderComposable?.invoke()
                                ?: placeholder?.let { Text(it, style = TextStyles.hint) }
                        }
                        innerTextField()
                    }
                )

                trailingIcon?.let {
                    Box(
                        modifier = Modifier.size(IconSizeMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        it()
                    }
                }
            }
        }

        AnimatedVisibility (!errorMessage.isNullOrEmpty()) {
            VerticalSpacer(Spacing8)
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.color.danger,
                style = TextStyles.captionMedium
            )
        }
    }
}
