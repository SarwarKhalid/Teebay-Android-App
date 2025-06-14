package com.example.teebay.presentation.screens.AddProduct.nestedscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.teebay.presentation.components.CategorySelector

@Composable
fun CategorySelectScreen(
    selectedCategories: List<String>,
    allCategories: List<String>,
    onCategoryToggle: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select Categories")
        CategorySelector(selectedCategories, allCategories, onCategoryToggle)
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(
                onClick = onBack
            ) {
                Text("Back")
            }
            Button(onClick = onNext, enabled = selectedCategories.isNotEmpty()) {
                Text("Next")
            }
        }
    }
}