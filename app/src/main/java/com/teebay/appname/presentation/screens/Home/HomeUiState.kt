package com.teebay.appname.presentation.screens.Home

import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result
import com.teebay.appname.core.model.User

data class HomeUiState(
    val user: User? = null,
    val isLoggedIn: Boolean? = null,
    val productsList: Result<List<Product>>? = null
)