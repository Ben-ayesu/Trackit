package com.example.trackit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.trackit.navigation.NavigationNavHost
import com.example.trackit.ui.theme.TrackitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackitTheme {
                val navController = rememberNavController()
                NavigationNavHost(navController = navController)
            }
        }
    }
}
