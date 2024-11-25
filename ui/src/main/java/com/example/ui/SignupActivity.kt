package com.example.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.components.atoms.CustomButton
import com.example.ui.theme.GraduationProjectTheme

class SignupActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GraduationProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GreetingPreview2() {
    GraduationProjectTheme {
        CustomButton(
            onClick = {

            },
            text = "hi"
        )
    }
}