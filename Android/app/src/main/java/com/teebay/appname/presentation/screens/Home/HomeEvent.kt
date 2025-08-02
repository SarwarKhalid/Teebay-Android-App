package com.teebay.appname.presentation.screens.Home

import com.teebay.appname.core.model.Product


sealed class HomeEvent {
    data object OnLogout : HomeEvent()
    data class OnDeleteProduct(val product: Product) : HomeEvent()
}