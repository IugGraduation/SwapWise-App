package com.example.ui.profile.composable

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    informationContent: @Composable () -> Unit,
    postsContent: @Composable () -> Unit,
    settingsContent: @Composable () -> Unit,

) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
    ) {

        when(pagerState.currentPage) {
            0 -> informationContent()
            1 -> postsContent()
            else -> settingsContent()
        }
    }
}