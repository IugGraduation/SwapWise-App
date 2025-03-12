package com.example.ui.profile.composable


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.theme.TextStyles.bodySmall
import com.example.ui.theme.TextStyles.headingMedium
import com.example.ui.theme.color

@Composable
fun ProfileDialog(
    title: String,
    text: String,
    onDismissButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = MaterialTheme.typography
    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = { onDismissButtonClick() },
        title = { Text(text = title, style = typography.titleMedium, color = MaterialTheme.color.textPrimary) },
        text = { Text(text = text, style = typography.bodySmall, color = MaterialTheme.color.textSecondary) },
        confirmButton = {
            TextButton(onClick = { onConfirmButtonClick() }) {
                Text(text = stringResource(R.string.confirm), style = bodySmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.color.danger)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissButtonClick() }) {
                Text(text = stringResource(R.string.dismiss), style = bodySmall, color = MaterialTheme.color.textSecondary)

            }
        },
        containerColor = MaterialTheme.color.onBackground
    )

}

@Preview(showBackground = true)
@Composable
fun ProfileDialogPreview() {
    ProfileDialog("title", "content", {}, {})
}
