package com.example.ui.components.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.ButtonMakeOfferBottom
import com.example.ui.components.atoms.ImageWithMaxSize
import com.example.ui.components.molecules.PostHeaderRow
import com.example.ui.components.molecules.PostTitleRow
import com.example.ui.models.Orientation
import com.example.domain.models.PostItem
import com.example.domain.models.User
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun PostCard(
    postItem: PostItem,
    orientation: Orientation,
    modifier: Modifier = Modifier
) {
    var myModifier = modifier.height(272.dp)
    myModifier = when (orientation) {
        Orientation.Horizontal -> myModifier.width(width = 248.dp)
        Orientation.Vertical -> myModifier.fillMaxWidth()
    }
    BoxRounded(
        color = MaterialTheme.colorScheme.onBackground,
        modifier = myModifier
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopHalfBox(postItem = postItem)
            BottomHalf(postItem = postItem)
        }
    }
}

@Composable
private fun TopHalfBox(postItem: PostItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.54f)
    ) {
        ImageWithMaxSize(
            painter = painterResource(postItem.imgResId),
            contentDescription = postItem.imgContentDescription,
            contentScale = ContentScale.Crop
        )
        PostHeaderRow(
            isOpen = postItem.isOpen,
            user = postItem.user
        )
    }
}

@Composable
private fun BottomHalf(postItem: PostItem) {
    PostTitleRow(postItem = postItem)
    Text(
        postItem.details,
        style = TextStyles.bodySmall,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(horizontal = Spacing8)
    )
    ButtonMakeOfferBottom(onClick = postItem.onClickMakeOffer)

}

@Preview(showBackground = true, showSystemUi = false, backgroundColor = 0xFFA41515)
@Composable
fun PreviewPostCard() {
    GraduationProjectTheme {
        PostCard(
            postItem = PostItem(
                imgResId = R.drawable.img_top_interactive,
                imgContentDescription = "",
                user = User(
                    name = "Jane Cooper",
                    imgResId = R.drawable.img_user_fake,
                    imgContentDescription = ""
                ),
                title = "10kg of Sugar Up for sadlkfjsdlkafjdsl ...",
                details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful ...",
                onClickMakeOffer = { },
                isOpen = true,
                offersCount = 4
            ),
            orientation = Orientation.Vertical,
        )
    }
}