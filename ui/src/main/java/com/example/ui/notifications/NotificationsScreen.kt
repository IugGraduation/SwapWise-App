package com.example.ui.notifications

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.model.Notification
import com.example.domain.model.NotificationGroup
import com.example.domain.notifications.GetFakeNotificationsUseCase
import com.example.domain.notifications.GroupNotificationsUseCase
import com.example.ui.R
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.templates.MainTitledScreenTemplate
import com.example.ui.models.BottomBarUiState
import com.example.ui.shared.BottomNavigationViewModel
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.Danger
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing56
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color
import kotlinx.coroutines.launch

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
        navController = navController
    )

    NotificationsContent(
        state = state,
        bottomBarState = bottomBarState,
        notificationsInteractions = viewModel,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationsContent(
    state: MyUiState<NotificationUIState>,
    bottomBarState: BottomBarUiState,
    notificationsInteractions: INotificationsInteractions
) {
    MainTitledScreenTemplate(
        title = stringResource(R.string.notifications),
        bottomBarState = bottomBarState,
    ) {
        val groupedNotifications = notificationsInteractions.getGroupedNotifications()

        LazyColumn(
            modifier = Modifier.padding(top = Spacing16),
            verticalArrangement = Arrangement.spacedBy(Spacing8)
        ) {
            groupedNotifications.forEach { group ->
                stickyHeader {
                    Text(
                        text = group.title,
                        style = TextStyles.headingSmall,
                        color = MaterialTheme.color.textTertiary,
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


@Composable
fun SwipeableNotificationCard(
    notification: Notification,
    onDismiss: (id: String) -> Unit
) {
    val offset = remember { Animatable(0f) }
    val swipeThreshold = with(LocalDensity.current) { 200.dp.toPx() }
    val scope = rememberCoroutineScope()

    Box {
        BackgroundDeleteCard()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer { translationX = offset.value }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            // Determine if the card should be dismissed
                            val targetOffset = when {
                                offset.value > swipeThreshold -> swipeThreshold * 2
                                offset.value < -swipeThreshold -> -swipeThreshold * 2
                                else -> 0f
                            }

                            if (targetOffset == 0f) {
                                // Snap back if threshold isn't crossed
                                scope.launch { offset.animateTo(0f) }
                            } else {
                                // Trigger dismissal
                                scope.launch {
                                    offset.animateTo(targetOffset)
                                    onDismiss(notification.id)
                                }
                            }
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                offset.snapTo(offset.value + dragAmount) // Update the drag position
                            }
                        }
                    )
                }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing16)
                    .clip(RoundedCornerShape(100))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.color.onBackground),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(notification.userImage),
                        contentDescription = stringResource(R.string.user_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(Spacing4)
                            .size(48.dp)
                    )
                    Text(
                        text = notification.message,
                        style = TextStyles.bodySmall,
                        color = MaterialTheme.color.textPrimary,
                        modifier = Modifier.padding(horizontal = Spacing4, vertical = Spacing8)

                    )
                }
            }
        }
    }
}

@Composable
private fun BackgroundDeleteCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(Spacing56)
            .padding(horizontal = Spacing16)
            .clip(RoundedCornerShape(100)),
        colors = CardDefaults.cardColors().copy(
            containerColor = Danger,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing16),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painterResource(R.drawable.ic_trash_bin),
                contentDescription = stringResource(R.string.remove_notification),
                tint = BackgroundLight
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewNotificationContent() {
    GraduationProjectTheme {

        val notificationUIState = NotificationUIState(
            notifications = GetFakeNotificationsUseCase()()
        )
        NotificationsContent(
            state = MyUiState(notificationUIState),
            bottomBarState = BottomBarUiState(selectedItem = 2),
            notificationsInteractions = object : INotificationsInteractions {
                override fun onDismiss(id: String) {}
                override fun getGroupedNotifications(): List<NotificationGroup> {
                    return GroupNotificationsUseCase()(GetFakeNotificationsUseCase()())
                }
            },
        )
    }
}