package com.example.ui.components.atoms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.theme.Primary
import com.example.ui.theme.TextStyles

@Composable
fun SwapWiseTextButton(onClick: () -> Unit, text: String) {
    Box(modifier = Modifier.clickable(onClick = onClick)) {
        Text(
            text,
            style = TextStyles.headingSmall,
            color = Primary,
            modifier = Modifier.padding(0.dp),
        )
    }
}


//@Preview
//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewSwapWiseTextButton() {
    SwapWiseTextButton({}, "Login")
}