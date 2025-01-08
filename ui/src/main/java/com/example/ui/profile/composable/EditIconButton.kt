package com.example.ui.profile.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.theme.IconButtonSize40
import com.example.ui.theme.IconSizeMedium
import com.example.ui.theme.Primary
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.WhitePrimary
import com.example.ui.theme.ZeroDp

@Composable
fun EditIconButton(modifier: Modifier = Modifier, onClick:() -> Unit) {
    Button(
        modifier = modifier
            .size(IconButtonSize40),
        contentPadding = PaddingValues(ZeroDp),
        shape = RoundedCornerShape(RadiusLarge),
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(
                contentColor = Primary,
                containerColor = WhitePrimary
            ),
    ) {
        Icon(
            modifier = Modifier
                .size(IconSizeMedium),
            painter = painterResource(id = R.drawable.ic_pen),
            contentDescription = null,
            tint = Primary
        )
    }
}

@Preview
@Composable
private fun EditIconButtonPreview() {
    EditIconButton {  }
}