package com.sam.ui.post_details.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sam.ui.R
import com.sam.ui.components.atoms.BoxRounded
import com.sam.ui.theme.IconSize32
import com.sam.ui.theme.IconSize16
import com.sam.ui.theme.Primary
import com.sam.ui.theme.Spacing16
import com.sam.ui.theme.TextStyles
import com.sam.ui.theme.color

@Composable
fun PhoneRow(
    phone: String,
    onClickPhoneButton: () -> Unit,
    onClickWhatsappButton: () -> Unit,
    onClickMessageButton: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = Spacing16)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = phone,
            style = TextStyles.bodyLarge,
            color = MaterialTheme.color.textTertiary
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing16)) {
            RoundedIconButton(
                onClick = onClickPhoneButton,
                iconResId = R.drawable.ic_phone,
                contentDescription = stringResource(R.string.phone)
            )
            RoundedIconButton(
                onClick = onClickWhatsappButton,
                iconResId = R.drawable.ic_chat,
                contentDescription = stringResource(R.string.what_s_up_app)
            )
            RoundedIconButton(
                onClick = onClickMessageButton,
                iconResId = R.drawable.ic_message,
                contentDescription = stringResource(R.string.message)
            )
        }
    }
}

@Composable
fun RoundedIconButton(
    onClick: () -> Unit,
    iconResId: Int,
    contentDescription: String = "",
    modifier: Modifier = Modifier
) {
    BoxRounded(
        color = Primary,
        modifier = modifier
            .size(IconSize32)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painterResource(iconResId),
            contentDescription = contentDescription,
            modifier = Modifier.size(IconSize16),
            tint = Color.White
        )
    }
}
