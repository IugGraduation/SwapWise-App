package com.example.ui.components.organisms

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import com.example.domain.GetPostsUseCase
import com.example.domain.models.OfferItem
import com.example.domain.models.PostItem
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.ButtonMakeOfferBottom
import com.example.ui.components.atoms.ImageWithMaxWidth
import com.example.ui.components.molecules.PostHeaderRow
import com.example.ui.components.molecules.PostTitleRow
import com.example.ui.models.Orientation
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles


@Composable
fun TopicCard(
    item: OfferItem,
    orientation: Orientation,
    modifier: Modifier = Modifier,
) {
    var myModifier = modifier.height(272.dp)
    myModifier = when (orientation) {
        Orientation.Horizontal -> myModifier.width(width = 248.dp)
        Orientation.Vertical -> myModifier.fillMaxWidth()
    }
    if(item is PostItem){
        item.onClickGoToDetails?.let {
            myModifier = myModifier.clickable { it() }
        }
    }
    BoxRounded(
        color = MaterialTheme.colorScheme.onBackground,
        modifier = myModifier
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopHalfBox(item = item)
            BottomHalf(item = item)
        }
    }
}


@Composable
private fun TopHalfBox(item: OfferItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.54f)
    ) {
        ImageWithMaxWidth(
            painter = painterResource(item.imgResId),
            contentDescription = item.imgContentDescription,
            contentScale = ContentScale.Crop
        )
        var isOpen = true
        var isOfferItem = true
        if(item is PostItem){
            isOpen = item.isOpen
            isOfferItem = false
        }
        PostHeaderRow(
            isOpen = isOpen,
            user = item.user,
            isOfferCard = isOfferItem,
            modifier = Modifier.padding(Spacing8)
        )
    }
}

@Composable
private fun BottomHalf(item: OfferItem) {
    PostTitleRow(item = item)
    Text(
        item.details,
        style = TextStyles.bodySmall,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(horizontal = Spacing8)
    )
    var onClickMakeOffer = {}
    if (item is PostItem){
        onClickMakeOffer = item.onClickMakeOffer
    }
    ButtonMakeOfferBottom(onClick = onClickMakeOffer)

}

//@Preview(showBackground = true, showSystemUi = false, backgroundColor = 0xFFA41515)
@Composable
fun PreviewPostCard() {
    GraduationProjectTheme {
        TopicCard(
            item = GetPostsUseCase()()[0],
            orientation = Orientation.Vertical,
        )
    }
}