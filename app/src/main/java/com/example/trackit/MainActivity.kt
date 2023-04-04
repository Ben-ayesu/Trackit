package com.example.trackit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackit.navigation.NavigationNavHost
import com.example.trackit.ui.screens.LoginViewModel
import com.example.trackit.ui.theme.TrackitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            TrackitTheme {
                NavigationNavHost(loginViewModel = loginViewModel)
            }
        }
    }
}
