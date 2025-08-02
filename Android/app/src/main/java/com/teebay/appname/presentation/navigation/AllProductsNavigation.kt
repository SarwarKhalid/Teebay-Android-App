package com.teebay.appname.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.teebay.appname.core.model.Product
import com.teebay.appname.presentation.screens.AllProducts.AllProductsScreen
import com.teebay.appname.presentation.screens.AllProducts.AllProductsViewModel
import kotlinx.serialization.Serializable

@Serializable
object AllProductsRoute

fun NavGraphBuilder.allProductsDestination(
    navController: NavController,
    onNavigateToProductDetails: (Product) -> Unit,
) {
    composable<AllProductsRoute> {
        val viewModel: AllProductsViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        AllProductsScreen(
            navController = navController,
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onNavigateToProductDetails = onNavigateToProductDetails,
        )
    }
}

fun NavController.navigateToAllProducts() {
    navigate(route = AllProductsRoute)
}

fun NavController.navigatePopUpToAllProducts() {
    navigate(route = AllProductsRoute) {
        popUpTo(AllProductsRoute) { inclusive = false }
    }
}

