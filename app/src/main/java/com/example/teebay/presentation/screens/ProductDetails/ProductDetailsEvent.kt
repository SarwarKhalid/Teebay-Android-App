package com.example.teebay.presentation.screens.ProductDetails

import com.example.teebay.core.model.Product

sealed class ProductDetailsEvent {
    data class BuyProduct(val product: Product) : ProductDetailsEvent()
}