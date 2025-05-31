package com.example.teebay.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.teebay.presentation.screens.Home.HomeScreen
import com.example.teebay.presentation.screens.Home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavGraphBuilder.homeDestination(onNavigateToLogin: () -> Unit) {
    composable<HomeRoute> {
        val viewModel: HomeViewModel = hiltViewModel()
        val homeUiState by viewModel.uiState.collectAsStateWithLifecycle()
        HomeScreen(homeUiState,onNavigateToLogin)
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