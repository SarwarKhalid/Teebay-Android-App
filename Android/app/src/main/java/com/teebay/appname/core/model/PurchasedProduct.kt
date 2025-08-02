package com.teebay.appname.core.model

data class PurchasedProduct(
    val id: Int,
    val buyer: Int,
    val seller: Int,
    val product: Int,
    val purchaseDate: String
)