package com.example.ui.components.molecules

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.model.User
import com.example.ui.components.atoms.HeaderRow
import com.example.ui.components.atoms.UserHeader

@Composable
fun PostHeaderRow(
    user: User,
    isOpen: Boolean = true,
    isOfferCard: Boolean,
    modifier: Modifier = Modifier,
) {
    HeaderRow(modifier = modifier) {
        UserHeader(user)
        if (!isOfferCard)
            BoxStatus(isOpen = isOpen)
    }
}