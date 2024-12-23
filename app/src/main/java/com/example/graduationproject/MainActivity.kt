package com.example.graduationproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.ui.add_offer.navigateToAddOffer
import com.example.ui.home.navigateToHome
import com.example.ui.theme.GraduationProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GraduationProjectTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
                navController.navigateToHome()
            }
        }
    }
}
