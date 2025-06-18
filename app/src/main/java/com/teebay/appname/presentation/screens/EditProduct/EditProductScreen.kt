package com.teebay.appname.presentation.screens.EditProduct

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teebay.appname.presentation.components.CategorySelector
import com.teebay.appname.presentation.components.RentOptionSelector

@Composable
fun EditProductScreen(
    uiState: EditProductUiState,
    onEvent: (EditProductEvent) -> Unit,
    onNavigateToHome: () -> Unit
) {
    val allCategories = listOf(
        "electronics",
        "furniture",
        "home_appliances",
        "toys",
        "sporting_goods",
        "outdoor"
    ) //Placeholder for categories
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text("Edit product")
        OutlinedTextField(
            value = uiState.title,
            onValueChange = { onEvent(EditProductEvent.TitleChanged(it)) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        CategorySelector(uiState.categories, allCategories, onCategoryToggle = {
            onEvent(EditProductEvent.CategoryToggled(it))
        })

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.description,
            onValueChange = { onEvent(EditProductEvent.DescriptionChanged(it)) },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp, max = 400.dp),
            maxLines = 10
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.purchasePrice,
            onValueChange = { onEvent(EditProductEvent.PurchasePriceChanged(it)) },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.rentPrice,
            onValueChange = { onEvent(EditProductEvent.RentPriceChanged(it)) },
            label = { Text("Rent") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        RentOptionSelector(
            uiState.rentOption,
            onRentOptionChange = { onEvent(EditProductEvent.RentOptionChanged(it)) }
        )

        Button(
            onClick = {
                onEvent(EditProductEvent.Submit)
                onNavigateToHome()
            },
            enabled = submitButtonEnabled(uiState),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
    }
}

private fun submitButtonEnabled(uiState: EditProductUiState): Boolean {
    uiState.run {
        return title.isNotBlank() && description.isNotBlank() && categories.isNotEmpty() && purchasePrice.isNotBlank() && rentPrice.isNotBlank() && rentOption.isNotBlank()
    }
}
