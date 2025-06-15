package com.example.teebay.presentation.screens.AllProducts

import com.example.teebay.core.model.Product

sealed class AllProductsEvent {
    data class OnProductClicked(val product: Product) : AllProductsEvent()
}