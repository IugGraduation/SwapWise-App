package com.example.ui.components.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing56
import com.example.ui.theme.typography


@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Spacing56),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(RadiusLarge)
    ) {
        Text(text, style = typography.headingMedium)
    }
}


//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    GraduationProjectTheme {
        CustomButton(
            onClick = {

            },
            text = "hi"
        )
    }
}