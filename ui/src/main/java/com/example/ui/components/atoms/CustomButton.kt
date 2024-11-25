package com.example.ui.components.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Primary
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16


@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing16)
    ) {
        Button(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(RadiusLarge)
        ) {
            Text(text, modifier = Modifier.padding(vertical = Spacing16))
        }
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    GraduationProjectTheme(darkTheme = false) {
        CustomButton(
            onClick = {

            },
            text = "hi"
        )
    }
}