package com.example.teebay.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.teebay.presentation.screens.Home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavGraphBuilder.homeDestination() {
    composable<HomeRoute> {
        HomeScreen("Welcome to Home") // Placeholder
    }
}

fun NavController.navigatePopUpToHome() {
    navigate(route = HomeRoute) {
        // Pops all destinations from the back stack up to and including the start destination of the current navigation graph.
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
        }
    }
}