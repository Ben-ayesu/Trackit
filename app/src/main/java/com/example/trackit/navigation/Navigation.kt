package com.example.trackit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trackit.ui.screens.NotesScreen
import com.example.trackit.ui.screens.SignInScreen

sealed class Destination(val route: String) {
    object Login : Destination("login")
    object Notes : Destination("notes")
}

@Composable
fun NavigationNavHost(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "login") {
        composable(Destination.Login.route) {
            SignInScreen(context, navController)
        }
        composable(Destination.Notes.route) {
            NotesScreen()
        }
    }
}