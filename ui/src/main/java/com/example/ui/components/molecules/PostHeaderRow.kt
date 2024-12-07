package com.example.ui.components.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.domain.models.User
import com.example.ui.theme.IconSizeSmall
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun PostHeaderRow(isOpen: Boolean, user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing8),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painter = painterResource(user.imgResId),
                contentDescription = user.imgContentDescription,
                modifier = Modifier.size(IconSizeSmall)
            )
            HorizontalSpacer(Spacing4)
            Text(
                text = user.name,
                style = TextStyles.headingSmall,
                color = MaterialTheme.colorScheme.background
            )
        }
        BoxStatus(isOpen = isOpen)
    }
}