package com.teebay.appname.presentation.screens.ProductDetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar

private val TAG = "ProductDetailsScreen"

@Composable
fun ProductDetailsScreen(
    uiState: ProductDetailsUiState,
    onEvent: (ProductDetailsEvent) -> Unit,
    onNavigateToAllProducts: () -> Unit
) {
    val product = uiState.product ?: return
//    var showConfirmDialog by remember { mutableStateOf(false) } //TODO: Move to UI state holder

    Column(modifier = Modifier
        .padding(24.dp)
        .padding(top = 70.dp)
    ) {
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onEvent(ProductDetailsEvent.ShowRentDialog) },
                modifier = Modifier.padding(24.dp)
            ) {
                Text("Rent")
            }

            Button(
                onClick = { onEvent(ProductDetailsEvent.ShowConfirmPurchaseDialog) },
                modifier = Modifier.padding(24.dp)
            ) {
                Text("Buy")
            }
        }
    }

    // Confirmation dialog
    if (uiState.confirmPurchaseDialog) {
        AlertDialog(
            onDismissRequest = { onEvent(ProductDetailsEvent.DismissConfirmPurchaseDialog) },
            title = { Text("Confirm Purchase") },
            text = { Text("Are you sure you want to buy \"${product.title}\" for ${product.purchasePrice}?") },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(ProductDetailsEvent.DismissConfirmPurchaseDialog)
                    onEvent(ProductDetailsEvent.BuyProduct(uiState.product))
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { onEvent(ProductDetailsEvent.DismissConfirmPurchaseDialog) }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Rent Confirmation dialog
    if (uiState.showRentDialog) {
        RentDialog(
            uiState = uiState,
            product = product,
            startDateTime = uiState.rentStartDateTime,
            endDateTime = uiState.rentEndDateTime,
            onSetRentTimes = { start, end ->
                onEvent(ProductDetailsEvent.SetRentDateTimes(start, end))
            },
            onDismiss = { onEvent(ProductDetailsEvent.DismissRentDialog) },
            onConfirm = {
                onEvent(ProductDetailsEvent.DismissRentDialog)
                onEvent(ProductDetailsEvent.ConfirmRent)
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
                Log.i(TAG, "Product purchase failed: $status")
                onNavigateToAllProducts()
            }

            null -> {}
        }
    }

    uiState.rentProductStatus.let { status ->
        when (status) {
            is Result.Success -> {
                Log.i(TAG, "Product rented: ${status.data}")
                onNavigateToAllProducts()
            }

            is Result.Failure -> {
                Log.i(TAG, "Product rent failed: $status")
                onNavigateToAllProducts()
            }

            null -> {}
        }
    }
}

@Composable
private fun RentDialog(
    uiState: ProductDetailsUiState,
    product: Product?,
    startDateTime: LocalDateTime?,
    endDateTime: LocalDateTime?,
    onSetRentTimes: (LocalDateTime, LocalDateTime) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val context = LocalContext.current
    var start by remember { mutableStateOf<LocalDateTime?>(startDateTime) }
    var end by remember { mutableStateOf<LocalDateTime?>(endDateTime) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Rent \"${product?.title}\"") },
        text = {
            Column {
                TextButton(onClick = {
                    pickDateTime(context) { pickedDateTime -> start = pickedDateTime }
                }) {
                    Text("Start Datetime: ${start?.toString() ?: "Select"}")
                }

                TextButton(onClick = {
                    pickDateTime(context) { pickedDateTime -> end = pickedDateTime }
                }) {
                    Text("End Datetime: ${end?.toString() ?: "Select"}")
                }

                if (start != null && end != null && !end!!.isBefore(start)) {
                    onSetRentTimes(start!!, end!!)
                    Text("Total Cost: $${String.format("%.2f", uiState.totalRentCost.toFloat())}")
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = start != null && end != null && !end!!.isBefore(start),
                onClick = {
                    if (start != null && end != null) onConfirm()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun pickDateTime(context: Context, onPicked: (LocalDateTime) -> Unit) {
    val now = LocalDateTime.now()
    val pickedDateTime = Calendar.getInstance()
    DatePickerDialog(context, { _, y, m, d ->
        pickedDateTime.set(y, m, d)
        TimePickerDialog(context, { _, hour, min ->
            pickedDateTime.set(Calendar.HOUR_OF_DAY, hour)
            pickedDateTime.set(Calendar.MINUTE, min)
            val dt = pickedDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            onPicked(dt)
        }, now.hour, now.minute, false).show()
    }, now.year, now.monthValue - 1, now.dayOfMonth).show()
}
