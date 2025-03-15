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
import com.example.ui.home.navigateToHome
import com.example.ui.models.BottomBarUiState
import com.example.ui.notifications.navigateToNotifications
import com.example.ui.profile.navigateToProfile
import com.example.ui.search.navigateToSearch
import com.example.ui.theme.Primary
import com.example.ui.theme.color

@Composable
fun BottomBarTemplate(
    bottomBarState: BottomBarUiState,
    floatingActionButton: @Composable () -> Unit = {},
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
                        0 -> bottomBarState.navController?.navigateToHome()
                        1 -> bottomBarState.navController?.navigateToSearch()
                        2 -> bottomBarState.navController?.navigateToNotifications()
                        3 -> bottomBarState.navController?.navigateToProfile()
                    }
                }
            )
        },
        floatingActionButton = floatingActionButton,
        baseUiState = baseUiState,
    ) {
        content()
    }
}


@Composable
private fun SwapWiseBottomNavigationBar(selectedItem: Int, onClickItem: (itemNum: Int) -> Unit) {
    val navigationBarItemColors = NavigationBarItemDefaults.colors().copy(
        selectedIconColor = MaterialTheme.color.onBackground,
        selectedTextColor = Primary,
        selectedIndicatorColor = Primary,
        unselectedIconColor = MaterialTheme.color.textTertiary,
    )

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(MaterialTheme.color.textTertiary)
        )
        NavigationBar(
            containerColor = MaterialTheme.color.background,
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
                        contentDescription = stringResource(R.string.notification)
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