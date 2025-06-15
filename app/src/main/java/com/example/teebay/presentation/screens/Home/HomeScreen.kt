package com.example.teebay.presentation.screens.Home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result
import com.example.teebay.presentation.components.DrawerScaffold

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToMyProducts: () -> Unit,
    onNavigateToAddProduct: () -> Unit,
    onNavigateToEditProduct: (Product) -> Unit,
    onNavigateToAllProducts: () -> Unit
) {
    if (uiState.isLoggedIn == true) {
        DrawerScaffold(
            onMyProductsClick = onNavigateToMyProducts,
            onLogout = {
                onEvent(HomeEvent.OnLogout)
                onNavigateToLogin()
            },
            title = "My Products",
            onAllProductsClick = { onNavigateToAllProducts() },
            floatingActionButton = {
                FloatingActionButton(onClick = { onNavigateToAddProduct() }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Product")
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                var productToDelete by remember { mutableStateOf<Product?>(null) }

                if (uiState.productsList is Result.Success) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(uiState.productsList.data) { product ->
                            ProductCard(
                                product = product,
                                onDeleteClick = { productToDelete = product },
                                onNavigateToEditProduct = onNavigateToEditProduct
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    }

                    // Confirmation dialog
                    productToDelete?.let { product ->
                        AlertDialog(
                            onDismissRequest = { productToDelete = null },
                            title = { Text("Confirm Delete") },
                            text = { Text("Are you sure you want to delete '${product.title}'?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    onEvent(HomeEvent.OnDeleteProduct(product))
                                    productToDelete = null
                                }) {
                                    Text("Delete")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { productToDelete = null }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }
                } else if (uiState.productsList is Result.Failure) {
                    Text("No products found")
                }
            }
        }
    } else if (uiState.isLoggedIn == false) {
        onNavigateToLogin()
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onDeleteClick: () -> Unit,
    onNavigateToEditProduct: (Product) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToEditProduct(product) }
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    product.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = onDeleteClick,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Black)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Product")
                }
            }

            Spacer(Modifier.height(4.dp))

            Text("Categories: ${product.categories.joinToString()}")
            Text("Purchase Price: ${product.purchasePrice}")
            Text("Rent Price: ${product.rentPrice}/${product.rentOption}")
            Text("Description: ${product.description}")
            Text("Posted on: ${product.datePosted}")
        }
    }
}