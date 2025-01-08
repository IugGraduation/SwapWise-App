package com.example.ui.profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.ui.R
import com.example.ui.theme.BlackFourth
import com.example.ui.theme.BorderHeight24
import com.example.ui.theme.BorderWidth2

@Composable
fun UserActivitiesBar(
    modifier: Modifier = Modifier,
                    postsNumber: String,
                    offersNumber: String,
                    exchangesNumber: String
) {
    Row (
        modifier = modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
        ){
        VerticalBoldAndLightText(boldText = postsNumber, lightText = stringResource(R.string.posts))
        InfoBoarder(modifier = Modifier.align(Alignment.CenterVertically))
        VerticalBoldAndLightText(boldText = offersNumber, lightText = stringResource(R.string.offers))
        InfoBoarder(modifier = Modifier.align(Alignment.CenterVertically))
        VerticalBoldAndLightText(boldText = exchangesNumber, lightText = stringResource(R.string.exchanges))
    }
}

@Composable
fun InfoBoarder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(BorderWidth2)
            .height(BorderHeight24)
            .background(color = BlackFourth)
    )
}