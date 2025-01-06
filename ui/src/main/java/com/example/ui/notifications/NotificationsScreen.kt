package com.example.ui.notifications

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.GetFakeNotificationsUseCase
import com.example.ui.R
import com.example.ui.components.atoms.SwipeableNotificationCard
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.templates.MainTitledScreenTemplate
import com.example.ui.home.navigateToHome
import com.example.ui.models.BottomBarUiState
import com.example.ui.search.navigateToSearch
import com.example.ui.shared.BottomNavigationViewModel
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel(),
    bottomNavigationViewModel: BottomNavigationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val selectedItem by bottomNavigationViewModel.selectedItem.collectAsState()
    val bottomBarState = BottomBarUiState(
        selectedItem = selectedItem,
        onItemSelected = bottomNavigationViewModel::onItemSelected,
        navigateToHome = { navController.navigateToHome() },
        navigateToSearch = { navController.navigateToSearch() },
        navigateToNotifications = { navController.navigateToNotifications() },
        navigateToProfile = { navController.navigateToHome() },
    )

    NotificationsContent(
        state = state,
        bottomBarState = bottomBarState,
        notificationsInteractions = viewModel,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationsContent(
    state: NotificationUIState,
    bottomBarState: BottomBarUiState,
    notificationsInteractions: NotificationsInteractions
) {
    MainTitledScreenTemplate(
        title = stringResource(R.string.notifications),
        bottomBarState = bottomBarState,
    ) {
        val groupedNotifications = state.groupNotifications()

        LazyColumn(
            modifier = Modifier.padding(top = Spacing16),
            verticalArrangement = Arrangement.spacedBy(Spacing8)
        ) {
            groupedNotifications.forEach { group ->
                stickyHeader {
                    Text(
                        text = group.title,
                        style = TextStyles.headingSmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(horizontal = Spacing16)
                            .padding(bottom = Spacing8)
                    )
                }
                items(group.notifications, key = { it.id }) { notification ->
                    SwipeableNotificationCard(notification, notificationsInteractions::onDismiss)
                }

                item {
                    VerticalSpacer(Spacing8)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewNotificationContent() {
    GraduationProjectTheme {

        val notificationUIState = NotificationUIState(
            notifications = GetFakeNotificationsUseCase()()
        )
        NotificationsContent(
            state = notificationUIState,
            bottomBarState = BottomBarUiState(selectedItem = 2),
            notificationsInteractions = object :NotificationsInteractions{
                override fun onDismiss(id: String) { }
            },
        )
    }
}