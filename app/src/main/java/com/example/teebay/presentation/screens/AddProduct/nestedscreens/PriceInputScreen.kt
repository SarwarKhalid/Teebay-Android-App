package com.example.teebay.presentation.screens.AddProduct.nestedscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PriceInputScreen(
    purchasePrice: String,
    rentPrice: String,
    rentOption: String,
    onPurchasePriceChange: (String) -> Unit,
    onRentPriceChange: (String) -> Unit,
    onRentOptionChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = purchasePrice,
            onValueChange = onPurchasePriceChange,
            label = { Text("Purchase Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = rentPrice,
            onValueChange = onRentPriceChange,
            label = { Text("Rent Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            listOf("day", "hour").forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    RadioButton(
                        selected = rentOption == it,
                        onClick = { onRentOptionChange(it) }
                    )
                    Text(it)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(
                onClick = onBack
            ) {
                Text("Back")
            }
            Button(
                onClick = onNext,
                enabled = purchasePrice.isNotBlank() && rentPrice.isNotBlank() && rentOption.isNotBlank()
            ) {
                Text("Next")
            }
        }
    }
}
