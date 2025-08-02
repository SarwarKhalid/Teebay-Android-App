package com.teebay.appname.presentation.screens.AddProduct.nestedscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teebay.appname.presentation.components.CategorySelector
import com.teebay.appname.presentation.components.NextAndBackButton

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
        NextAndBackButton(
            onNext = onNext,
            onNextText = "Next",
            onBack = onBack,
            onBackText = "Back",
            onNextEnabled = selectedCategories.isNotEmpty()
        )
    }
}