package com.example.ui.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.domain.model.IOffer
import com.example.ui.R
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.components.atoms.RoundedIconButton
import com.example.ui.theme.Spacing16
import com.example.ui.theme.TextStyles

@Composable
fun PhoneRow(state: IOffer) {
    Row(
        modifier = Modifier
            .padding(horizontal = Spacing16)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = state.user.phone,
            style = TextStyles.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        Row {
            RoundedIconButton(
                onClick = { },
                iconResId = R.drawable.ic_phone,
                contentDescription = stringResource(R.string.phone)
            )
            HorizontalSpacer(Spacing16)
            RoundedIconButton(
                onClick = { },
                iconResId = R.drawable.ic_chat,
                contentDescription = stringResource(R.string.what_s_up_app)
            )
            HorizontalSpacer(Spacing16)
            RoundedIconButton(
                onClick = { },
                iconResId = R.drawable.ic_message,
                contentDescription = stringResource(R.string.message)
            )
        }
    }
}