package com.example.ui.components.molecules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.theme.CardWidth
import com.example.ui.theme.IconSize16
import com.example.ui.theme.ImageSize140
import com.example.ui.theme.ImageSize16
import com.example.ui.theme.ImageSize200
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
    location: String,
    modifier: Modifier = Modifier,
    isOpen: Boolean = true,
    showState: Boolean = false,
    isHorizontalCard: Boolean = false,
    onCardClick: () -> Unit,
) {

    Card(
        modifier = modifier
            .wrapContentHeight() // Allow height to adjust to content
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isHorizontalCard) ImageSize140 else ImageSize200) // Fixed image height
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = postImage,
                    contentDescription = details,
                    contentScale = ContentScale.Crop
                )

                PostHeaderSection(
                    userImage = userImage,
                    username = username,
                    isOpen = isOpen,
                    showState = showState
                )
            }
            
            VerticalSpacer(Spacing8)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = MaterialTheme.color.onBackground)
            ) {
                PostInfoSection(
                    title = title,
                    details = details,
                    location = location
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
    isOpen: Boolean = true,
    showState: Boolean
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

        AnimatedVisibility(visible = showState) { PostStateRoundedBox(isPostOpen = isOpen) }
    }
}

@Composable
private fun PostInfoSection(
    title: String,
    details: String,
    location: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = Spacing8)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = Spacing8),
            text = title,
            style = TextStyles.headingMedium,
            color = MaterialTheme.color.textPrimary,
            maxLines = 2, // Allow title to take more lines if needed
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.padding(horizontal = Spacing8),
            text = details,
            style = TextStyles.bodySmall,
            maxLines = 3,
            color = MaterialTheme.color.textSecondary,
            overflow = TextOverflow.Ellipsis
        )

        VerticalSpacer(Spacing8)

        Row(
            modifier = Modifier.padding(horizontal = Spacing8, vertical = Spacing4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing4)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = location,
                tint = MaterialTheme.color.textTertiary,
                modifier = Modifier.size(IconSize16)
            )
            Text(
                text = location,
                style = TextStyles.captionLarge,
                color = MaterialTheme.color.textTertiary
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


@Preview(showSystemUi = true, apiLevel = 34)
@Composable
private fun PostCardPreview() {
    PostCard(
        username = "Bilal Alkhatib",
        userImage = painterResource(R.drawable.img_user_fake),
        isOpen = true,
        title = "Liters of Olive Oil for Trade",
        details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful. This is a very long description to test the flexible height of the card. Let's see how many lines it takes and if the card grows accordingly.",
        location = "Gaza",
        postImage = painterResource(R.drawable.img_food_and_beverages),
        isHorizontalCard = true
    ) { }
}
