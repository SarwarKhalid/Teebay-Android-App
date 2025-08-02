package com.teebay.appname.core.model

data class RentedProduct(
    val id: Int,
    val renter: Int,
    val seller: Int,
    val product: Int,
    val rentOption: String,
    val startDate: String,
    val endDate: String,
    val totalPrice: String,
    val rentDate: String,
)
