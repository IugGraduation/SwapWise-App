package com.example.ui.components.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.theme.BorderWidth2
import com.example.ui.theme.NormalButtonHeight
import com.example.ui.theme.Primary
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.TextStyles
import com.example.ui.theme.WhitePrimary

@Composable
fun SwapWiseOutlineButton(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .height(NormalButtonHeight)
            .fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary, containerColor = WhitePrimary),
        shape = RoundedCornerShape(RadiusLarge),
        border = BorderStroke(width = BorderWidth2, color = Primary),
        onClick = { onClick() }
    ) {
        Text(
            text = title,
            style = TextStyles.headingMedium
        )
    }
}