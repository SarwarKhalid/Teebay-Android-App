package com.teebay.appname.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.teebay.appname.presentation.screens.EditProduct.EditProductScreen
import com.teebay.appname.presentation.screens.EditProduct.EditProductViewModel
import kotlinx.serialization.Serializable

@Serializable
data class EditProductRoute(
    val id: Int,
    val title: String,
    val categories: List<String>,
    val description: String,
    val purchasePrice: String,
    val rentPrice: String,
    val rentOption: String
)

fun NavGraphBuilder.editProductDestination(onNavigateToHome: () -> Unit) {
    composable<EditProductRoute> { navBackStackEntry ->
        val viewModel: EditProductViewModel = hiltViewModel()

        //Used LaunchedEffect so that setProduct() will only be called once.
        LaunchedEffect(key1 = Unit) {
            navBackStackEntry.toRoute<EditProductRoute>().run {
                viewModel.setProduct(
                    id = id,
                    title = title,
                    categories = categories,
                    description = description,
                    purchasePrice = purchasePrice,
                    rentPrice = rentPrice,
                    rentOption = rentOption
                )
            }
        }
        val editProductUiState by viewModel.uiState.collectAsStateWithLifecycle()
        EditProductScreen(editProductUiState, viewModel::onEvent, onNavigateToHome)
    }
}

fun NavController.navigateToEditProduct(
    id: Int,
    title: String,
    categories: List<String>,
    description: String,
    purchasePrice: String,
    rentPrice: String,
    rentOption: String
) {
    navigate(
        route = EditProductRoute(
            id = id,
            title = title,
            categories = categories,
            description = description,
            purchasePrice = purchasePrice,
            rentPrice = rentPrice,
            rentOption = rentOption
        )
    )
}