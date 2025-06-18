package com.teebay.appname.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun CategorySelector(
    selectedCategories: List<String>,
    allCategories: List<String>,
    onCategoryToggle: (String) -> Unit,
) {
    allCategories.forEach { category ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = selectedCategories.contains(category),
                onCheckedChange = { onCategoryToggle(category) }
            )
            Text(category)
        }
    }
}