package com.example.teebay.presentation.screens.ProductDetails

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teebay.core.model.Result

private val TAG = "ProductDetailsScreen"

@Composable
fun ProductDetailsScreen(
    uiState: ProductDetailsUiState,
    onEvent: (ProductDetailsEvent) -> Unit,
    onNavigateToAllProducts: () -> Unit
) {
    val product = uiState.product ?: return
    var showConfirmDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(24.dp).padding(top = 70.dp),) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(product.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "Categories: ${product.categories.joinToString()}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Purchase Price: $${product.purchasePrice}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Rent Price: $${product.rentPrice}/${product.rentOption}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            Button(
                onClick = { showConfirmDialog = true },
                modifier = Modifier
//                .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                Text("Buy")
            }
        }
    }

    // Confirmation dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirm Purchase") },
            text = { Text("Are you sure you want to buy \"${product.title}\" for ${product.purchasePrice}?") },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    onEvent(ProductDetailsEvent.BuyProduct(uiState.product))
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    uiState.buyProductStatus.let { status ->
        when (status) {
            is Result.Success -> {
                Log.i(TAG, "Product purchased: ${status.data}")
                onNavigateToAllProducts()
            }

            is Result.Failure -> {
                Log.i(TAG, "Product purchased: $status")
                onNavigateToAllProducts()
            }

            null -> {}
        }
    }
}