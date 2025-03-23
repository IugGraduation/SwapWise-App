package com.example.ui.home.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.ui.R
import com.example.ui.theme.IconButtonSize60
import com.example.ui.theme.IconSizeLarge
import com.example.ui.theme.ZeroDp
import com.example.ui.theme.color

@Composable
fun AddIconButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .size(IconButtonSize60),
        contentPadding = PaddingValues(ZeroDp),
        shape = CircleShape,
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(
                contentColor = Color.White,
                containerColor = MaterialTheme.color.primary
            ),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add),
            modifier = Modifier.size(IconSizeLarge)
        )
    }
}