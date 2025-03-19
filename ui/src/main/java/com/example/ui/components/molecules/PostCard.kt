package com.example.ui.components.molecules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.theme.ButtonSize32
import com.example.ui.theme.CardHeight
import com.example.ui.theme.CardWidth
import com.example.ui.theme.IconSizeSmall
import com.example.ui.theme.ImageSize16
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryOverlay
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun PostCard(
    userImage: Painter,
    postImage: Painter,
    username: String,
    title: String,
    details: String,
    modifier: Modifier = Modifier,
    offersNumber: String = "0",
    isOpen: Boolean = true,
    isPostCard: Boolean = true,
    isHorizontalCard: Boolean = false,
    onMakeOfferButtonClick: () -> Unit = {},
    onCardClick: () -> Unit,
) {

    Card(
        modifier = modifier
            .height(CardHeight)
            .then(
                if (isHorizontalCard) {
                    Modifier.width(width = CardWidth)
                } else {
                    Modifier.fillMaxWidth()
                }
            ),
        onClick = onCardClick,
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.color.onBackground)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1.6f)) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = postImage,
                    contentDescription = details,
                    contentScale = ContentScale.Crop
                )

                PostHeaderSection(
                    userImage = userImage,
                    username = username,
                    isPostCard = isPostCard,
                    isOpen = isOpen
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.4f)
                    .background(color = MaterialTheme.color.onBackground)
            ) {
                PostInfoSection(
                    title = title,
                    offersNumber = offersNumber,
                    details = details,
                    isPostCard = isPostCard,
                    onMakeOfferButtonClick = onMakeOfferButtonClick,
                )
            }
        }
    }
}

@Composable
private fun PostHeaderSection(
    userImage: Painter,
    username: String,
    modifier: Modifier = Modifier,
    isPostCard: Boolean = true,
    isOpen: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing8),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing4)) {
            Image(
                modifier = Modifier
                    .size(ImageSize16)
                    .background(shape = CircleShape, color = MaterialTheme.color.transparent),
                painter = userImage,
                contentDescription = username,
                contentScale = ContentScale.Crop
            )

            Text(
                text = username,
                style = TextStyles.headingSmall.copy(
                    shadow = Shadow(
                        color = MaterialTheme.color.textPrimary,
                        blurRadius = 8f,
                        offset = Offset(2f, 2f)
                    ),
                ),
                color = MaterialTheme.color.textPrimary,
            )
        }

        AnimatedVisibility(isPostCard) { PostStateRoundedBox(isPostOpen = isOpen) }
    }
}

@Composable
private fun PostInfoSection(
    title: String,
    offersNumber: String,
    details: String,
    isPostCard: Boolean,
    onMakeOfferButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing8),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(end = Spacing8)
                    .weight(3f),
                text = title,
                style = TextStyles.headingMedium,
                color = MaterialTheme.color.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(isPostCard) {
                BoxRounded(
                    modifier = Modifier
                        .wrapContentWidth(),
                    color = MaterialTheme.color.background
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = Spacing8, vertical = Spacing4),
                        horizontalArrangement = Arrangement.spacedBy(Spacing4),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_offer),
                            contentDescription = offersNumber + stringResource(R.string.offers),
                            modifier = Modifier.size(IconSizeSmall),
                            tint = MaterialTheme.color.textSecondary
                        )
                        Text(
                            text = offersNumber + " " + stringResource(R.string.offers),
                            style = TextStyles.captionSmall,
                            color = MaterialTheme.color.textSecondary
                        )
                    }
                }
            }
        }

        Text(
            modifier = Modifier.padding(horizontal = Spacing8),
            text = details,
            style = TextStyles.bodySmall,
            maxLines = 3,
            color = MaterialTheme.color.textSecondary,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(isPostCard) {
            ButtonMakeOfferBottom(
                onClick = { onMakeOfferButtonClick() }
            )
        }
    }
}

@Composable
private fun ButtonMakeOfferBottom(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(color = PrimaryOverlay)
            .height(ButtonSize32)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = Spacing4),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_offer),
                contentDescription = "",
                modifier = Modifier.size(IconSizeSmall),
                tint = Primary
            )
            HorizontalSpacer(Spacing4)
            Text(
                stringResource(R.string.make_your_offer),
                style = TextStyles.captionMedium,
                color = Primary
            )
        }
    }
}

@Composable
private fun PostStateRoundedBox(
    modifier: Modifier = Modifier,
    isPostOpen: Boolean = true,
) {
    BoxRounded(
        modifier = modifier,
        color = if (isPostOpen) MaterialTheme.color.correctOverlay else MaterialTheme.color.dangerOverlay
    ) {
        Text(
            text = if (isPostOpen) stringResource(R.string.open) else stringResource(R.string.closed),
            modifier = Modifier.padding(horizontal = Spacing8, vertical = Spacing4),
            color = if (isPostOpen) MaterialTheme.color.correct else MaterialTheme.color.danger,
            style = TextStyles.captionSmall.copy(fontWeight = FontWeight.SemiBold),
        )
    }
}


@Preview(showSystemUi = true)
@Composable
private fun PostCardPreview() {
    PostCard(
        username = "Bilal Alkhatib",
        userImage = painterResource(R.drawable.img_user_fake),
        isOpen = true,
        title = "Liters of Olive Oil for Trade Liters of Olive Oil for Trade",
        details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar Liters of Olive Oil for Trade that I’d like to exchange Liters of Olive Oil for Trade Liters of Olive Oil for Trade Liters of Olive Oil for Trade for something useful",
        offersNumber = "50",
        postImage = painterResource(R.drawable.img_food_and_beverages),
        isHorizontalCard = true
    ) { }
}


