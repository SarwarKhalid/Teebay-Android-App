package com.teebay.appname.framework.network.response

import com.google.gson.annotations.SerializedName
import com.teebay.appname.core.model.PurchasedProduct

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

fun PurchaseProductResponse.toPurchasedProduct(): PurchasedProduct {
    return PurchasedProduct(
        id = id,
        buyer = buyer,
        seller = seller,
        product = product,
        purchaseDate = purchaseDate
    )
}