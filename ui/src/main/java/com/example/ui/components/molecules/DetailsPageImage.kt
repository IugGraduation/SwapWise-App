package com.example.ui.components.molecules

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.ui.R
import com.example.ui.theme.BlackFourth
import com.example.ui.theme.RadiusLarge

@Composable
fun DetailsPageImage(
    image: String?,
    contentScale: ContentScale = ContentScale.Crop,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var imgPainter: Painter = rememberAsyncImagePainter(image)
    var myContentScale = contentScale
    if (image.isNullOrEmpty()) {
        imgPainter = painterResource(R.drawable.ic_add_image)
        myContentScale = ContentScale.Inside
    }
    val myModifier = if(onClick != null) Modifier.clickable { onClick() } else Modifier
    Box(modifier = myModifier) {
        Image(
            painter = imgPainter,
            contentDescription = stringResource(R.string.image),
            contentScale = myContentScale,
            modifier = modifier
                .fillMaxWidth()
                .height(270.dp)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = RadiusLarge, bottomStart = RadiusLarge
                    )
                )
                .background(color = BlackFourth)
        )
    }
}