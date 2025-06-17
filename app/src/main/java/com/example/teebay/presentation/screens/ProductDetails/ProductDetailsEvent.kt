package com.example.teebay.presentation.screens.ProductDetails

import com.example.teebay.core.model.Product
import java.time.LocalDateTime

sealed class ProductDetailsEvent {
    data class BuyProduct(val product: Product) : ProductDetailsEvent()
    object ShowConfirmPurchaseDialog : ProductDetailsEvent()
    object DismissConfirmPurchaseDialog : ProductDetailsEvent()
    object ShowRentDialog : ProductDetailsEvent()
    data class SetRentDateTimes(val start: LocalDateTime, val end: LocalDateTime) : ProductDetailsEvent()
    object ConfirmRent : ProductDetailsEvent()
    object DismissRentDialog : ProductDetailsEvent()
}