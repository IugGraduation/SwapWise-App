package com.example.ui.components.atoms


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.ui.R
import com.example.ui.theme.RadioButtonsSize
import com.example.ui.theme.RadiusExtraLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.color

@Composable
fun MultiChoiceDialog(
    onClickDone: (choice: String) -> Unit,
    onDismissRequest: () -> Unit = {},
    choices: List<String>,
    oldSelectedChoice: String,
) {
    val (selected) = choices.map { rememberSaveable { mutableStateOf(oldSelectedChoice) } }
    val color = MaterialTheme.color
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(RadiusExtraLarge))
                .size(RadioButtonsSize)
                .background(color.background)
                .padding(Spacing24)
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                choices.forEach { text ->
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable {
                            selected.value = text
                        },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = color.textPrimary
                        )
                        RadioButton(
                            selected = text == selected.value,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = color.primary,
                                unselectedColor = color.gray
                            ),
                            onClick = { selected.value = text }
                        )
                    }
                }

                SwapWiseFilledButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { onClickDone(selected.value) },
                    text = stringResource(id = R.string.done),
                )
            }
        }
    }
}
