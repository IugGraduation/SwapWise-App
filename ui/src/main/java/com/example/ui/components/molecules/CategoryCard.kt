package com.example.ui.components.molecules

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.CardText
import com.example.ui.components.atoms.ImageWithMaxSize
import com.example.domain.models.CategoryItem
import com.example.ui.models.Orientation
import com.example.ui.theme.TextStyles

@Composable
fun CategoryCard(
    categoryItem: CategoryItem,
    orientation: Orientation,
    modifier: Modifier = Modifier
) {
    val myModifier = when(orientation){
        Orientation.Horizontal -> modifier.size(width = 108.dp, height = 80.dp)
        Orientation.Vertical -> modifier.fillMaxWidth().height(height = 164.dp)
    }

    BoxRounded(modifier = myModifier, contentAlignment = Alignment.Center) {
        ImageWithMaxSize(
            painter = painterResource(categoryItem.imgResId),
            contentDescription = categoryItem.imgContentDescription,
            contentScale = ContentScale.Crop
        )
        BoxRounded(modifier = Modifier.fillMaxSize())

        val textStyle = when (orientation){
            Orientation.Horizontal -> TextStyles.smallCustomTitle
            Orientation.Vertical -> TextStyles.largeCustomTitle
        }
        CardText(text = categoryItem.title, textStyle = textStyle)
    }
}