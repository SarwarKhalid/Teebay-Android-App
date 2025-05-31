package com.example.teebay.presentation.screens.Home

import com.example.teebay.core.model.User

data class HomeUiState(
    val user: User? = null,
    val isLoggedIn: Boolean? = null
)
