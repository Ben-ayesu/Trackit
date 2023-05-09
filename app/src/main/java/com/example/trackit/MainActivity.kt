package com.example.trackit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackit.navigation.Navigation
import com.example.trackit.ui.Viewmodels.DetailViewModel
import com.example.trackit.ui.Viewmodels.HomeViewModel
import com.example.trackit.ui.Viewmodels.LoginViewModel
import com.example.trackit.ui.theme.TrackitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
            val detailViewModel = viewModel(modelClass = DetailViewModel::class.java)
            TrackitTheme {
                Navigation(
                    loginViewModel = loginViewModel,
                    detailViewModel = detailViewModel,
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}
