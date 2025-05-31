package com.example.teebay.presentation.screens.Home

import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result
import com.example.teebay.core.model.User

data class HomeUiState(
    val user: User? = null,
    val isLoggedIn: Boolean? = null,
    val productsList: Result<List<Product>>? = null
)
