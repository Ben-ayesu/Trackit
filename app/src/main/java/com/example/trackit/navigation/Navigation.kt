package com.example.trackit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trackit.ui.screens.HomeScreen
import com.example.trackit.ui.screens.LoginViewModel
import com.example.trackit.ui.screens.SignInScreen
import com.example.trackit.ui.screens.SignUpScreen

sealed class Destination(val route: String) {
    object Login : Destination("login")
    object Home : Destination("home")
    object SignUp : Destination("signup")
}

@Composable
fun NavigationNavHost(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Login.route
    ) {
        composable(Destination.Login.route) {
            SignInScreen(
                loginViewModel = loginViewModel,
                onNavToHomePage = {
                    navController.navigate(Destination.Home.route) {
                        launchSingleTop = true
                        popUpTo(Destination.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onNavToSignUpPage = {
                    navController.navigate(Destination.SignUp.route) {
                        launchSingleTop = true
                        popUpTo(Destination.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Destination.SignUp.route) {
            SignUpScreen(
                onNavToHomePage = {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.SignUp.route) {
                            inclusive = true
                        }
                    }
                },
                onNavToSignUpPage = {
                    navController.navigate(Destination.Login.route)
                },
                loginViewModel = loginViewModel
            )

        }

        composable(Destination.Home.route) {
            HomeScreen()
        }
    }
}