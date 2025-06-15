package com.example.teebay.presentation.screens.AllProducts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result
import com.example.teebay.presentation.components.DrawerScaffold

@Composable
fun AllProductsScreen(
    uiState: AllProductsUiState,
    onEvent: (AllProductsEvent) -> Unit,
    onNavigateToProductDetails: (Product) -> Unit,
    onNavigateToMyProducts: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToAllProducts: () -> Unit
) {
    DrawerScaffold(
        title = "All Products",
        onMyProductsClick = onNavigateToMyProducts,
        onAllProductsClick =  onNavigateToAllProducts,
        onLogout = onNavigateToLogin
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            when (val result = uiState.productsList) {
                is Result.Success -> {
                    LazyColumn {
                        items(result.data) { product ->
                            ProductCard(
                                product = product,
                                onClick = {
                                    onEvent(AllProductsEvent.OnProductClicked(product))
                                    onNavigateToProductDetails(product)
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }

                is Result.Failure -> {
                    Text("Failed to load products.")
                }

                null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(product.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Categories: ${product.categories.joinToString()}")
            Text("Price: ${product.purchasePrice}")
            Text("Rent: ${product.rentPrice}/${product.rentOption}")

            // Truncated description with ellipsis
            Text(
                text = product.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
