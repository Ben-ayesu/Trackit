package com.example.trackit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.trackit.ui.Viewmodels.DetailViewModel
import com.example.trackit.ui.Viewmodels.HomeViewModel
import com.example.trackit.ui.Viewmodels.LoginViewModel
import com.example.trackit.ui.screens.DetailScreen
import com.example.trackit.ui.screens.HomeScreen
import com.example.trackit.ui.screens.SignInScreen
import com.example.trackit.ui.screens.SignUpScreen

sealed class Destination(val route: String) {
    object Login : Destination("login")
    object Home : Destination("home")
    object SignUp : Destination("signup")
}

enum class LoginRoutes {
    Signup,
    SignIn
}

enum class HomeRoutes {
    Home,
    Detail
}

enum class NestedRoutes {
    Main,
    Login
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NestedRoutes.Main.name
    ) {
        authGraph(navController, loginViewModel)
        homeGraph(
            navController = navController,
            detailViewModel,
            homeViewModel
        )
    }
}

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    navigation(
        startDestination = LoginRoutes.SignIn.name,
        route = NestedRoutes.Login.name
    ) {
        composable(LoginRoutes.SignIn.name) {
            SignInScreen(
                loginViewModel = loginViewModel,
                onNavToHomePage = {
                    navController.navigate(NestedRoutes.Main.name) {
                        launchSingleTop = true
                        popUpTo(LoginRoutes.SignIn.name) {
                            inclusive = true
                        }
                    }
                },
                onNavToSignUpPage = {
                    navController.navigate(LoginRoutes.Signup.name) {
                        launchSingleTop = true
                        popUpTo(LoginRoutes.Signup.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = LoginRoutes.Signup.name) {
            SignUpScreen(
                onNavToHomePage = {
                    navController.navigate(NestedRoutes.Main.name) {
                        popUpTo(LoginRoutes.Signup.name) {
                            inclusive = true
                        }
                    }
                },
                onNavToSignUpPage = {
                    navController.navigate(LoginRoutes.SignIn.name)
                },
                loginViewModel = loginViewModel
            )

        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel
) {
    navigation(
        startDestination = HomeRoutes.Home.name,
        route = NestedRoutes.Main.name,
    ) {
        composable(HomeRoutes.Home.name) {
            HomeScreen(
                homeViewModel = homeViewModel,
                onNoteClick = { noteId ->
                    navController.navigate(
                        HomeRoutes.Detail.name + "?id=$noteId"
                    ) {
                        launchSingleTop = true
                    }
                },
                navtoNotesDetailPage = {
                    navController.navigate(HomeRoutes.Detail.name)
                }
            ) {
                navController.navigate(NestedRoutes.Login.name) {
                    launchSingleTop = true
                    popUpTo(0) {
                        inclusive = true
                    }
                }

            }
        }

        composable(
            route = HomeRoutes.Detail.name + "?id={id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { entry ->
            DetailScreen(
                detailViewModel = detailViewModel,
                noteId = entry.arguments?.getString("id") as String,
            ) {
                navController.navigateUp()

            }
        }
    }
}

