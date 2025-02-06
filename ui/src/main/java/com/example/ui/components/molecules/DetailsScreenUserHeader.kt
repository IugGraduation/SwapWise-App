package com.example.ui.components.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.model.User
import com.example.ui.R
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.IconSizeLarge
import com.example.ui.theme.IconSizeSmall
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing4
import com.example.ui.theme.TextStyles

@Composable
fun DetailsScreenUserHeader(user: User, date: String) {
    Row(
        modifier = Modifier.padding(horizontal = Spacing16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
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

@Composable
fun UserHeader(
    user: User,
    imgSize: Dp = IconSizeSmall,
    textStyle: TextStyle = TextStyles.headingSmall,
    textColor: Color = BackgroundLight
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(user.imageLink),
            contentDescription = user.imgContentDescription,
            modifier = Modifier.size(imgSize)
        )
        HorizontalSpacer(Spacing4)
        Text(
            text = user.name,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
private fun OverlayBoxWithImage(overlayColor: Color, imgResId: Int, text: String, modifier: Modifier = Modifier, boxModifier: Modifier = Modifier) {
    BoxRounded(color = overlayColor, modifier = boxModifier) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(imgResId),
                contentDescription = "",
                modifier = Modifier.size(12.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            HorizontalSpacer(Spacing4)
            Text(text, style = TextStyles.captionSmall)
        }
    }
}