package com.example.ui.components.molecules

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.components.atoms.ImageWithMaxWidth
import com.example.ui.theme.RadiusLarge

@Composable
fun PostDetailsImage(imgResId: Int, modifier: Modifier = Modifier) {
    ImageWithMaxWidth(
        painter = painterResource(R.drawable.img_top_interactive),
        contentDescription = stringResource(imgResId),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(270.dp)
            .clip(
                RoundedCornerShape(
                    bottomEnd = RadiusLarge, bottomStart = RadiusLarge
                )
            )
    )
}