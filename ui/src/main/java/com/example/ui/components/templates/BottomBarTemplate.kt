package com.example.ui.components.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.base.BaseUiState
import com.example.ui.models.BottomBarUiState
import com.example.ui.theme.Primary

@Composable
fun BottomBarTemplate(
    bottomBarState: BottomBarUiState,
    topBar: @Composable () -> Unit = {},
    baseUiState: BaseUiState = BaseUiState(),
    content: @Composable () -> Unit,
) {
    ScreenTemplate(
        topBar = topBar,
        bottomBar = {
            SwapWiseBottomNavigationBar(
                selectedItem = bottomBarState.selectedItem,
                onClickItem = { item ->
                    bottomBarState.onItemSelected(item)
                    when (item) {
                        0 -> bottomBarState.navigateToHome()
                        1 -> bottomBarState.navigateToSearch()
                        2 -> bottomBarState.navigateToNotifications()
                        3 -> bottomBarState.navigateToProfile()
                    }
                }
            )
        },
        baseUiState = baseUiState,
    ) {
        content()
    }
}


@Composable
private fun SwapWiseBottomNavigationBar(selectedItem: Int = 2, onClickItem: (itemNum: Int) -> Unit) {
    val navigationBarItemColors = NavigationBarItemDefaults.colors().copy(
        selectedIconColor = MaterialTheme.colorScheme.onBackground,
        selectedTextColor = Primary,
        selectedIndicatorColor = Primary,
        unselectedIconColor = MaterialTheme.colorScheme.tertiary,
    )

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(MaterialTheme.colorScheme.tertiary)
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_home),
                        contentDescription = stringResource(R.string.home)
                    )
                },
                selected = selectedItem == 0,
                onClick = { onClickItem(0) },
                label = { Text(stringResource(R.string.home)) },
                alwaysShowLabel = false,
                colors = navigationBarItemColors
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = stringResource(R.string.search)
                    )
                },
                selected = selectedItem == 1,
                onClick = { onClickItem(1) },
                label = { Text("Search") },
                alwaysShowLabel = false,
                colors = navigationBarItemColors
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_bell),
                        contentDescription = stringResource(R.string.notifications)
                    )
                },
                selected = selectedItem == 2,
                onClick = { onClickItem(2) },
                label = { Text(stringResource(R.string.notifications)) },
                alwaysShowLabel = false,
                colors = navigationBarItemColors
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_user),
                        contentDescription = stringResource(R.string.profile)
                    )
                },
                selected = selectedItem == 3,
                onClick = { onClickItem(3) },
                label = { Text(stringResource(R.string.profile)) },
                alwaysShowLabel = false,
                colors = navigationBarItemColors
            )
        }
    }
}