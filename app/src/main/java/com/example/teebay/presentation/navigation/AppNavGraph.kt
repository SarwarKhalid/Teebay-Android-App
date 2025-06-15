package com.example.teebay.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    //TODO: Add named parameters
    NavHost(navController, startDestination = HomeRoute) {
        loginDestination(
            onNavigateToHome = { navController.navigatePopUpToHome() },
            onNavigateToSignup = { navController.navigateToSignup() }

        )

        signupDestination(onNavigateToHome = { navController.navigatePopUpToHome() })

        homeDestination(
            onNavigateToLogin = { navController.navigateToLogin() },
            onNavigateToMyProducts = { navController.navigatePopUpToHome() },
            onNavigateToAddProduct = { navController.navigateToAddProduct() },
            onNavigateToEditProduct = { product ->
                product.run{
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
            onNavigateToAllProducts = { navController.navigateToAllProducts() }
        )

        allProductsDestination(
            onNavigateToMyProducts = { navController.navigatePopUpToHome() },
            onNavigateToProductDetails = {  },
            onNavigateToLogin = { navController.navigateToLogin() },
            onNavigateToAllProducts = { navController.navigateToAllProducts() }
        )

        addProductDestination(onNavigateToHome = { navController.navigatePopUpToHome() })

        editProductDestination(onNavigateToHome = { navController.navigatePopUpToHome() })
    }
}