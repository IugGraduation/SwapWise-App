package com.example.ui.components.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.model.User
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.IconSizeSmall
import com.example.ui.theme.Spacing4
import com.example.ui.theme.TextStyles

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