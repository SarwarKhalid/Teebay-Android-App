package com.teebay.appname.presentation.screens.Dashboard

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teebay.appname.core.model.Result
import androidx.compose.foundation.lazy.items
import com.teebay.appname.core.model.Product
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.teebay.appname.presentation.components.SideNavigationDrawer
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val TAG = "DashboardScreen"

@Composable
fun DashboardScreen(
    navController: NavController,
    uiState: DashboardUiState,
    onEvent: (DashboardEvent) -> Unit
) {

    SideNavigationDrawer(
        title = "Dashboard",
        navController = navController
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                DashboardTab.entries.forEach { tab ->
                    Log.i(TAG, tab.name)
                    FilterButton(
                        text = tab.name,
                        selected = uiState.selectedTab == tab,
                        onClick = { onEvent(DashboardEvent.OnTabSelected(tab)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val result = uiState.products) {
                null -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
//                        Text("Loading products.")
                    }
                }

                is Result.Success -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(result.data) { product ->
                            ProductItem(product)
                        }
                    }
                }

                is Result.Failure -> {
                    Text("Failed to load products.")
                }
            }
        }
    }
}

@Composable
fun FilterButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = if (selected) ButtonDefaults.buttonColors()
        else ButtonDefaults.outlinedButtonColors()
    ) {
        Text(text)
    }
}

@Composable
fun ProductItem(product: Product) {
    val formattedDate = try {
        ZonedDateTime.parse(product.datePosted)
            .format(DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.getDefault()))
    } catch (e: Exception) {
        product.datePosted // fallback to raw string
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(product.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text("Categories: ${product.categories.joinToString()}")
            Text("Purchase Price: ${product.purchasePrice}")
            Text("Rent: ${product.rentPrice}/${product.rentOption}")
            Text("Posted on: $formattedDate")
            Spacer(Modifier.height(4.dp))
            Text(
                text = product.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}