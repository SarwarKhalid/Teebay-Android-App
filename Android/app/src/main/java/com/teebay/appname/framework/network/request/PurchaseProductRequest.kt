package com.teebay.appname.framework.network.request

import com.google.gson.annotations.SerializedName

data class PurchaseProductRequest(
    @SerializedName("buyer")
    val buyer: Int,
    @SerializedName("product")
    val product: Int
)
