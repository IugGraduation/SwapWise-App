package com.example.ui.components.atoms

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.ui.theme.Gray
import com.example.ui.theme.Primary
import com.example.ui.theme.WhitePrimary

@Composable
fun SwapWiseSwitch(
    state: Boolean,
    onCheckedChange: (isCheck: Boolean) -> Unit
) {
    val checkState = rememberSaveable { mutableStateOf(state) }

    Switch(
        checked = checkState.value,
        onCheckedChange = {
            checkState.value = !checkState.value
            onCheckedChange(it)
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = WhitePrimary,
            uncheckedThumbColor = WhitePrimary,
            checkedBorderColor = Primary,
            uncheckedBorderColor = Gray,
            checkedTrackColor = Primary,
            uncheckedTrackColor = Gray
        )
    )
}