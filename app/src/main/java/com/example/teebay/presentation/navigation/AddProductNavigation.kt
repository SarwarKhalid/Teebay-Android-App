package com.example.teebay.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.teebay.presentation.screens.AddProduct.AddProductRootScreen
import com.example.teebay.presentation.screens.AddProduct.AddProductViewModel
import kotlinx.serialization.Serializable

@Serializable
object AddProductRoute

fun NavGraphBuilder.addProductDestination(onNavigateToHome: () -> Unit) {
    composable<AddProductRoute> {
        val viewModel: AddProductViewModel = hiltViewModel()
        val addProductUiState by viewModel.uiState.collectAsStateWithLifecycle()
        AddProductRootScreen(addProductUiState,viewModel::onEvent, onNavigateToHome)
    }
}

fun NavController.navigateToAddProduct() {
    navigate(route = AddProductRoute)
}