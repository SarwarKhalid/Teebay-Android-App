package com.example.teebay.presentation.screens.EditProduct

sealed class EditProductEvent {
    data class TitleChanged(val value: String) : EditProductEvent()
    data class CategoryToggled(val value: String) : EditProductEvent()
    data class DescriptionChanged(val value: String) : EditProductEvent()
    data class PurchasePriceChanged(val value: String) : EditProductEvent()
    data class RentPriceChanged(val value: String) : EditProductEvent()
    data class RentOptionChanged(val value: String) : EditProductEvent()
    object Submit : EditProductEvent()
}