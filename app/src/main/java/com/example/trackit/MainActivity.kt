package com.example.trackit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.trackit.ui.screens.SignInScreen
import com.example.trackit.ui.theme.TrackitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackitTheme {
                SignInScreen()
            }
        }
    }
}
