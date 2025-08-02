package com.teebay.appname.presentation.screens.ProductDetails

import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result
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