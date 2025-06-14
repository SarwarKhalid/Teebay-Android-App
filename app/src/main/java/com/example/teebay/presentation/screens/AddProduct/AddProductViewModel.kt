package com.example.teebay.presentation.screens.AddProduct

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teebay.core.domain.ProductUseCase
import com.example.teebay.core.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "AddProductViewModel"

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val userUseCase: UserUseCase,
    @ApplicationContext private val applicationContext: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> = _uiState

    fun onEvent(event: AddProductEvent) {
        when (event) {
            is AddProductEvent.TitleChanged -> _uiState.update { it.copy(title = event.value) }
            is AddProductEvent.CategoryToggled -> _uiState.update {
                val categories = it.categories.toMutableList()
                if (categories.contains(event.category)) categories.remove(event.category)
                else categories.add(event.category)
                it.copy(categories = categories)
            }

            is AddProductEvent.DescriptionChanged -> _uiState.update { it.copy(description = event.value) }
//            is AddProductEvent.SelectImage -> { /* trigger image picker */ }
            is AddProductEvent.ImageSelected -> _uiState.update { it.copy(imageUri = event.uri) }
            is AddProductEvent.PurchasePriceChanged -> _uiState.update { it.copy(purchasePrice = event.value) }
            is AddProductEvent.RentPriceChanged -> _uiState.update { it.copy(rentPrice = event.value) }
            is AddProductEvent.RentOptionChanged -> _uiState.update { it.copy(rentOption = event.value) }
            is AddProductEvent.Submit -> { uploadProduct(applicationContext) }
        }
    }

    private fun uploadProduct(context: Context) {
        Log.i(TAG, "uploadProduct")
        val imageUri = _uiState.value.imageUri
        viewModelScope.launch {
            userUseCase.getLoggedInUser().collect { user ->
                user?.let {
                    imageUri?.let {
                        productUseCase.uploadProduct(context, _uiState.value.toProduct(user.id), imageUri)
                    }
                }
            }
        }
    }


}