package com.sam.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sam.ui.components.atoms.VerticalSpacer
import com.sam.ui.theme.Danger
import com.sam.ui.theme.Spacing8
import com.sam.ui.theme.TextStyles

//this is not used now but will probably be needed later so I will let it stay
@Composable
fun ErrorMsgWrapper(
    errorMessage: String? = null,
    content: @Composable () -> Unit
) {
    Column {
        content()

        if (!errorMessage.isNullOrEmpty()) {
            VerticalSpacer(Spacing8)
            ErrorText(
                text = errorMessage,
            )
        }
    }
}

@Composable
private fun ErrorText(text: String = "", modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = TextStyles.captionMedium.copy(color = Danger),
        modifier = modifier
    )
}
