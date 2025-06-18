package com.teebay.appname.presentation.screens.AllProducts

import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result

data class AllProductsUiState(
    val productsList: Result<List<Product>>? = null
)