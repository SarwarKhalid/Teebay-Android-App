package com.teebay.appname.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.teebay.appname.presentation.screens.Dashboard.DashboardViewModel
import androidx.compose.runtime.getValue
import com.teebay.appname.presentation.screens.Dashboard.DashboardScreen

@kotlinx.serialization.Serializable
object DashboardRoute

fun NavGraphBuilder.dashboardDestination(
    navController: NavController
) {
    composable<DashboardRoute> {
        val viewModel: DashboardViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        DashboardScreen(
            navController = navController,
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }
}

fun NavController.navigateToDashboard() {
    navigate(route = DashboardRoute)
}
