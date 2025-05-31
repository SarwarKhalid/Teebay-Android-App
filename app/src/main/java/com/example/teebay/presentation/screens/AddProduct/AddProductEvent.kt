package com.example.teebay.presentation.screens.AddProduct

import android.content.Context
import android.net.Uri

sealed class AddProductEvent {
    data class TitleChanged(val value: String) : AddProductEvent()
    data class CategoryToggled(val category: String) : AddProductEvent()
    data class DescriptionChanged(val value: String) : AddProductEvent()
//    data object SelectImage : AddProductEvent()
    data class ImageSelected(val uri: Uri): AddProductEvent()
    data class PurchasePriceChanged(val value: String) : AddProductEvent()
    data class RentPriceChanged(val value: String) : AddProductEvent()
    data class RentOptionChanged(val value: String) : AddProductEvent()
    data class Submit(val context: Context) : AddProductEvent()
}