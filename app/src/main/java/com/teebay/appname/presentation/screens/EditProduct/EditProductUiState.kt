package com.teebay.appname.presentation.screens.EditProduct

import com.teebay.appname.core.model.Product

data class EditProductUiState(
    val title: String = "",
    val categories: List<String> = emptyList(),
    val description: String = "",
    val purchasePrice: String = "",
    val rentPrice: String = "",
    val rentOption: String = "",
)

fun EditProductUiState.toProduct(id: Int): Product {
    return Product(
        id = id,
        seller = -1,
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