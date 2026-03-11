package com.sam.ui.components.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sam.ui.theme.BorderWidth2
import com.sam.ui.theme.NormalButtonHeight
import com.sam.ui.theme.Primary
import com.sam.ui.theme.RadiusLarge
import com.sam.ui.theme.TextStyles
import com.sam.ui.theme.color

@Composable
fun SwapWiseOutlineButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        modifier = modifier
            .height(NormalButtonHeight)
            .fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.color.textPrimary,
            containerColor = MaterialTheme.color.transparent
        ),
        shape = RoundedCornerShape(RadiusLarge),
        border = BorderStroke(width = BorderWidth2, color = Primary),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            style = TextStyles.headingMedium
        )
    }
}