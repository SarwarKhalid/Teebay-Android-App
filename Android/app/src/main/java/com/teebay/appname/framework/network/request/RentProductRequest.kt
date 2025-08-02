package com.teebay.appname.framework.network.request

import com.google.gson.annotations.SerializedName

data class RentProductRequest(
    @SerializedName("renter")
    val renter: Int,
    @SerializedName("product")
    val product: Int,
    @SerializedName("rent_option")
    val rentOption: String,
    @SerializedName("rent_period_start_date")
    val startDate: String,
    @SerializedName("rent_period_end_date")
    val endDate: String
)