package com.example.teebay.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.teebay.core.model.Product
import com.example.teebay.presentation.screens.AllProducts.AllProductsScreen
import com.example.teebay.presentation.screens.AllProducts.AllProductsViewModel
import kotlinx.serialization.Serializable

@Serializable
object AllProductsRoute

fun NavGraphBuilder.allProductsDestination(
    onNavigateToMyProducts: () -> Unit,
    onNavigateToProductDetails: (Product) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToAllProducts: () -> Unit
) {
    composable<AllProductsRoute> {
        val viewModel: AllProductsViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        AllProductsScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onNavigateToMyProducts = onNavigateToMyProducts,
            onNavigateToProductDetails = onNavigateToProductDetails,
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToAllProducts = onNavigateToAllProducts
        )
    }
}

fun NavController.navigateToAllProducts() {
    navigate(route = AllProductsRoute)
}