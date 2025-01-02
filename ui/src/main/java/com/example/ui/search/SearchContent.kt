package com.example.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.GetFakeCategoriesNamesUseCase
import com.example.domain.GetFakePostsUseCase
import com.example.ui.R
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.atoms.CustomTextButton
import com.example.ui.components.atoms.CustomTextField
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.components.atoms.ImageWithMaxWidth
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.MainTitledScreenTemplate
import com.example.ui.models.BottomBarUiState
import com.example.ui.models.Chip
import com.example.ui.models.Orientation
import com.example.ui.models.TopicType
import com.example.ui.models.TopicUiState
import com.example.ui.theme.BlackFourth
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun SearchContent(
    state: SearchUiState,
    bottomBarState: BottomBarUiState,
    onSearchChange: (String) -> Unit,
    onClickTryAgain: () -> Unit,
) {
    val myTopic = TopicUiState(
        orientation = Orientation.Vertical,
        type = TopicType.RecentPosts,
        items = state.topicsList ?: listOf(),
    )

    MainTitledScreenTemplate(
        title = stringResource(R.string.search),
        bottomBarState = bottomBarState,
    ) {
        CustomTextField(
            value = state.search,
            onValueChange = onSearchChange,
            placeholder = stringResource(R.string.search),
            leadingIcon = {
                Row {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_search)
                    )
                    HorizontalSpacer(Spacing8)
                    Spacer(
                        Modifier
                            .height(Spacing24)
                            .width(1.5.dp)
                            .background(color = BlackFourth)
                    )
                }
            },
            modifier = Modifier.padding(horizontal = Spacing16)
        )
        VerticalSpacer(Spacing8)
        TitledChipsList(chipsList = state.filterChipsList)
        VerticalSpacer(Spacing16)
        if (state.topicsList == null || state.error != null) {
            EmptyContent(onClickTryAgain)
        } else if (state.isLoading) {
            LoadingContent()
        } else {
            CustomLazyLayout(topic = myTopic)
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight(0.8f)
    ) {
        ImageWithMaxWidth(
            painterResource(R.drawable.ic_searching),
            contentDescription = stringResource(R.string.searching)
        )
    }
}

@Composable
private fun EmptyContent(onClickTryAgain: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight(0.8f)
    ) {
        ImageWithMaxWidth(
            painterResource(R.drawable.ic_empty_box),
            contentDescription = stringResource(R.string.searching)
        )
        VerticalSpacer(Spacing8)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.nothing_found), style = TextStyles.hint)
            HorizontalSpacer(Spacing4)
            CustomTextButton(onClick = onClickTryAgain, text = stringResource(R.string.try_again))
        }
    }
}


//@Preview(showBackground = true)
@Composable
fun PreviewAllTopicsContent() {
    GraduationProjectTheme {
        val searchUiState = SearchUiState(
//            topicsList = GetFakePostsUseCase()(),
            filterChipsList = GetFakeCategoriesNamesUseCase()().map {
                Chip(text = it, onClick = { }, selected = false)
            },
//            isLoading = true,
        )
        SearchContent(
            state = searchUiState,
            bottomBarState = BottomBarUiState(selectedItem = 1),
            onSearchChange = { },
            onClickTryAgain = { },
        )
    }
}