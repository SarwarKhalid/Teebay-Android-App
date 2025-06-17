package com.example.teebay.presentation.screens.ProductDetails

import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result
import java.time.LocalDateTime

data class ProductDetailsUiState(
    val product: Product? = null,
    val buyProductStatus: Result<Any>? = null,
    val confirmPurchaseDialog: Boolean = false,
    val showRentDialog: Boolean = false,
    val rentStartDateTime: LocalDateTime? = null,
    val rentEndDateTime: LocalDateTime? = null,
    val totalRentCost: String = "0.0",
    val rentProductStatus: Result<Any>? = null,
)