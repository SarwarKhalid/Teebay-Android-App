package com.teebay.appname.framework.network.response

import com.google.gson.annotations.SerializedName

data class PurchaseProductResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("buyer")
    val buyer: Int,
    @SerializedName("seller")
    val seller: Int,
    @SerializedName("product")
    val product: Int,
    @SerializedName("purchase_date")
    val purchaseDate: String
)
