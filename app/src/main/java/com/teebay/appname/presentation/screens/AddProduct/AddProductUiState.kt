package com.teebay.appname.presentation.screens.AddProduct

import android.net.Uri
import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result

data class AddProductUiState(
    val title: String = "",
    val categories: List<String> = emptyList(),
    val description: String = "",
    val imageUri: Uri? = null,
    val purchasePrice: String = "",
    val rentPrice: String = "",
    val rentOption: String = "",
    val addProductStatus: Result<Any>? = null
)

fun AddProductUiState.toProduct(userId: Int): Product {
    return Product(
        id = 0,
        seller = userId,
        title = title,
        description = description,
        categories = categories,
        productImage = "",
        purchasePrice = purchasePrice,
        rentPrice = rentPrice,
        rentOption = rentOption,
        datePosted = ""
    )
}