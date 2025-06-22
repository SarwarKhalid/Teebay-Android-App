package com.teebay.appname.presentation.screens.EditProduct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teebay.appname.core.domain.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "EditProductViewModel"

@HiltViewModel
class EditProductViewModel @Inject constructor(private val productUseCase: ProductUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(EditProductUiState())
    val uiState: StateFlow<EditProductUiState> = _uiState
    private var productId: Int = -1

    fun onEvent(event: EditProductEvent) {
        when (event) {
            is EditProductEvent.TitleChanged -> {
                _uiState.update { it.copy(title = event.value) }
            }

            is EditProductEvent.CategoryToggled -> {
                _uiState.update {
                    val categories = it.categories.toMutableList()
                    if (categories.contains(event.value)) categories.remove(event.value)
                    else categories.add(event.value)
                    it.copy(categories = categories)
                }
            }

            is EditProductEvent.DescriptionChanged -> {
                _uiState.update { it.copy(description = event.value) }
            }

            is EditProductEvent.PurchasePriceChanged -> {
                _uiState.update { it.copy(purchasePrice = event.value) }
            }

            is EditProductEvent.RentPriceChanged -> {
                _uiState.update { it.copy(rentPrice = event.value) }
            }

            is EditProductEvent.RentOptionChanged -> {
                _uiState.update { it.copy(rentOption = event.value) }
            }

            EditProductEvent.Submit -> {
                viewModelScope.launch {
                    editProduct()
                }
            }
        }
    }

    fun setProduct(
        id: Int,
        title: String,
        categories: List<String>,
        description: String,
        purchasePrice: String,
        rentPrice: String,
        rentOption: String
    ) {
        Log.i(TAG, "setProduct")
        productId = id
        _uiState.update {
            it.copy(
                title = title,
                categories = categories,
                description = description,
                purchasePrice = purchasePrice,
                rentPrice = rentPrice,
                rentOption = rentOption
            )
        }
    }

    private fun editProduct() {
        Log.i(TAG, "editProduct")
        viewModelScope.launch {
            _uiState.update { it.copy(editProductStatus =  productUseCase.editProduct(_uiState.value.toProduct(productId))) }
        }
    }
}