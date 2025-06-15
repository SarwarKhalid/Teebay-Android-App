package com.example.teebay.presentation.screens.AllProducts

import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result

data class AllProductsUiState(
    val productsList: Result<List<Product>>? = null
)