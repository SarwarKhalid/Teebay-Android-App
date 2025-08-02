package com.teebay.appname.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.teebay.appname.core.model.Product
import com.teebay.appname.presentation.screens.ProductDetails.ProductDetailsScreen
import com.teebay.appname.presentation.screens.ProductDetails.ProductDetailsViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ProductDetailsRoute(val productJson: String)

fun NavGraphBuilder.productDetailsDestination(
    onNavigateToAllProducts: () -> Unit,
) {
    composable<ProductDetailsRoute> { navBackStackEntry ->
        val viewModel: ProductDetailsViewModel = hiltViewModel()
        Json.decodeFromString<Product>(navBackStackEntry.toRoute<ProductDetailsRoute>().productJson).also{
            viewModel.setProduct(it)
        }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        ProductDetailsScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onNavigateToAllProducts = onNavigateToAllProducts
        )
    }
}

fun NavController.navigateToProductDetails(product: Product) {
    navigate(route = ProductDetailsRoute(Json.encodeToString(product)))
}