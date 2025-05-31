package com.example.teebay.framework.network.response

import com.example.teebay.core.model.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("seller")
    val seller: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("categories")
    val categories: List<String>,

    @SerializedName("product_image")
    val productImage: String,

    @SerializedName("purchase_price")
    val purchasePrice: String,

    @SerializedName("rent_price")
    val rentPrice: String,

    @SerializedName("rent_option")
    val rentOption: String,

    @SerializedName("date_posted")
    val datePosted: String
)

fun ProductResponse.toProduct(): Product {
    return Product(
        id = id,
        seller = seller,
        title = title,
        description = description,
        categories = categories,
        productImage = productImage,
        purchasePrice = purchasePrice,
        rentPrice = rentPrice,
        rentOption = rentOption,
        datePosted = datePosted
    )
}