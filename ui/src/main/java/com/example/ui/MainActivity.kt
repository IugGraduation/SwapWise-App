package com.example.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.ui.login.LoginPage
import com.example.ui.theme.GraduationProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GraduationProjectTheme {
                LoginPage()
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    GraduationProjectTheme {
    }
}