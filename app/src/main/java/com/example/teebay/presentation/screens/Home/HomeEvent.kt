package com.example.teebay.presentation.screens.Home

import com.example.teebay.core.model.Product


sealed class HomeEvent {
    data object OnLogout : HomeEvent()
    data class OnDeleteProduct(val product: Product) : HomeEvent()
}