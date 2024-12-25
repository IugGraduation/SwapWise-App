package com.example.ui.components.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Primary
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Secondary
import com.example.ui.theme.Spacing56
import com.example.ui.theme.TextStyles


@Composable
fun CustomOutlinedButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Spacing56),
        enabled = enabled,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Primary,
        ),
        border = BorderStroke(1.dp, Primary),
        shape = RoundedCornerShape(RadiusLarge)
    ) {
        Text(text, style = TextStyles.headingMedium)
    }
}


//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewCustomOutlinedButton() {
    GraduationProjectTheme {
        CustomOutlinedButton(
            onClick = {

            },
            text = "hi"
        )
    }
}