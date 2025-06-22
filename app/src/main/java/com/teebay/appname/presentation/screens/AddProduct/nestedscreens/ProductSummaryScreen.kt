package com.teebay.appname.presentation.screens.AddProduct.nestedscreens

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.teebay.appname.core.model.Result
import com.teebay.appname.presentation.components.NextAndBackButton

@Composable
fun ProductSummaryScreen(
    addProductStatus: Result<Any>?,
    title: String,
    categories: List<String>,
    description: String,
    imageUri: Uri?,
    purchasePrice: String,
    rentPrice: String,
    rentOption: String,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Review and Submit", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("Title: $title")
        Text("Categories: ${categories.joinToString()}")
        Text("Description: $description")
        Text("Purchase Price: $purchasePrice")
        Text("Rent Price: $rentPrice")
        Text("Rent Option: $rentOption")
        Spacer(Modifier.height(8.dp))
        imageUri?.let {
            AsyncImage(model = it, contentDescription = "Product Image", modifier = Modifier.size(200.dp))
        }
        Spacer(Modifier.height(16.dp))
        NextAndBackButton(
            onNext = onSubmit,
            onNextText = "Submit",
            onBack = onBack,
            onBackText = "Back",
            onNextEnabled = true
        )
    }

    when(addProductStatus) {
        is Result.Success -> onNavigateToHome()
        is Result.Failure -> onNavigateToHome()
        null -> {}
    }
}
