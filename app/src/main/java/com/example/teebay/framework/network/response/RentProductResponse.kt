package com.example.teebay.framework.network.response

import com.google.gson.annotations.SerializedName

data class RentProductResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("renter")
    val renter: Int,
    @SerializedName("seller")
    val seller: Int,
    @SerializedName("product")
    val product: Int,
    @SerializedName("rent_option")
    val rentOption: String,
    @SerializedName("rent_period_start_date")
    val startDate: String,
    @SerializedName("rent_period_end_date")
    val endDate: String,
    @SerializedName("total_price")
    val totalPrice: String,
    @SerializedName("rent_date")
    val rentDate: String,
)