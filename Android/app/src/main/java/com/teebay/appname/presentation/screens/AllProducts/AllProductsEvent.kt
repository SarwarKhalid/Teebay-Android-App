package com.teebay.appname.presentation.screens.AllProducts

import com.teebay.appname.core.model.Product

sealed class AllProductsEvent {
    data class OnProductClicked(val product: Product) : AllProductsEvent()
}