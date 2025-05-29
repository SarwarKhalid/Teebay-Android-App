package com.example.teebay.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    //TODO: Change start destination to Home
    NavHost(navController, startDestination = LoginRoute) {
        loginDestination {
            navController.navigatePopUpToHome()
        }
        homeDestination()
    }
}