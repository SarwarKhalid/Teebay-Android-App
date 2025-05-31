package com.example.teebay.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    //TODO: Change start destination to Home
    NavHost(navController, startDestination = HomeRoute) {
        loginDestination(
            onNavigateToHome = { navController.navigatePopUpToHome() },
            onNavigateToSignup = { navController.navigateToSignup() }

        )
        signupDestination {
            navController.navigatePopUpToHome()
        }

        homeDestination(
            onNavigateToLogin = { navController.navigateToLogin() },
            onNavigateToMyProducts = { navController.navigatePopUpToHome() },
            onNavigateToAddProduct = { navController.navigateToAddProduct() }
        )

        addProductDestination { navController.navigatePopUpToHome() }
    }
}