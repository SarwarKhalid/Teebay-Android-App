package com.teebay.appname.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.teebay.appname.core.model.Product
import com.teebay.appname.presentation.screens.Home.HomeScreen
import com.teebay.appname.presentation.screens.Home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data class HomeRoute(val notificationProductId: Int? = null)

fun NavGraphBuilder.homeDestination(
    navController: NavController,
    onNavigateToLogin: () -> Unit,
    onNavigateToAddProduct: () -> Unit,
    onNavigateToEditProduct: (Product) -> Unit,
) {
    composable<HomeRoute> { navBackStackEntry ->
        val viewModel: HomeViewModel = hiltViewModel()
        val homeUiState by viewModel.uiState.collectAsStateWithLifecycle()
        viewModel.setNotificationProductId(navBackStackEntry.toRoute<HomeRoute>().notificationProductId)
        HomeScreen(
            navController = navController,
            uiState = homeUiState,
            onEvent = viewModel::onEvent,
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToAddProduct = onNavigateToAddProduct,
            onNavigateToEditProduct = onNavigateToEditProduct,
        )
    }
}

fun NavController.navigatePopUpToHome() {
    navigate(route = HomeRoute()) {
        // Pops all destinations from the back stack up to and including the start destination of the current navigation graph.
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
        }
    }
}

fun NavController.navigatePopUpToHomeWithNotificationId(notificationProductId: Int) {
    navigate(route = HomeRoute(notificationProductId)) {
        // Pops all destinations from the back stack up to and including the start destination of the current navigation graph.
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
        }
    }
}