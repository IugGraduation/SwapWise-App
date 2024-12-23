package com.example.ui.components.molecules

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.domain.model.User
import com.example.ui.R
import com.example.ui.components.atoms.HeaderRow
import com.example.ui.components.atoms.OverlayBoxWithImage
import com.example.ui.components.atoms.UserHeader
import com.example.ui.theme.IconSizeLarge
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing4
import com.example.ui.theme.TextStyles

@Composable
fun PostDetailsUserHeader(user: User, date: String) {
    HeaderRow(modifier = Modifier.padding(horizontal = Spacing16)) {
        UserHeader(
            user = user,
            imgSize = IconSizeLarge,
            textStyle = TextStyles.headingMedium,
            textColor = MaterialTheme.colorScheme.primary
        )
        OverlayBoxWithImage(
            overlayColor = MaterialTheme.colorScheme.onBackground,
            imgResId = R.drawable.ic_calendar,
            text = date,
            modifier = Modifier.padding(Spacing4),
            boxModifier = Modifier.shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(RadiusLarge),
                spotColor = Color.Black.copy(alpha = 0.2f),
                ambientColor = Color.Black.copy(alpha = 0.2f)
            )
        )
    }
}