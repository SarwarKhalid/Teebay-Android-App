package com.example.teebay.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = HomeRoute) {
        loginDestination(
            onNavigateToHome = { navController.navigatePopUpToHome() },
            onNavigateToSignup = { navController.navigateToSignup() }

        )
        signupDestination(onNavigateToHome = { navController.navigatePopUpToHome() })
        homeDestination(
            navController = navController,
            onNavigateToLogin = { navController.navigateToLogin() },
            onNavigateToAddProduct = { navController.navigateToAddProduct() },
            onNavigateToEditProduct = { product ->
                product.run {
                    navController.navigateToEditProduct(
                        id = id,
                        title = title,
                        categories = categories,
                        description = description,
                        purchasePrice = purchasePrice,
                        rentPrice = rentPrice,
                        rentOption = rentOption
                    )
                }
            },
        )
        addProductDestination(onNavigateToHome = { navController.navigatePopUpToHome() })
        editProductDestination(onNavigateToHome = { navController.navigatePopUpToHome() })
        allProductsDestination(
            navController = navController,
            onNavigateToProductDetails = { product ->
                navController.navigateToProductDetails(product)
            }
        )
        productDetailsDestination(
            onNavigateToAllProducts = { navController.navigatePopUpToAllProducts() }
        )
    }
}