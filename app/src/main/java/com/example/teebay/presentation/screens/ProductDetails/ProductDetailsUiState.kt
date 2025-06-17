package com.example.teebay.presentation.screens.ProductDetails

import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result

data class ProductDetailsUiState(
    val product: Product? = null,
    val buyProductStatus: Result<Any>? = null
)