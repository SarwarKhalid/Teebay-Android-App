package com.example.teebay.presentation.screens.AllProducts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teebay.core.domain.ProductUseCase
import com.example.teebay.core.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "AllProductsViewModel"

@HiltViewModel
class AllProductsViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllProductsUiState())
    val uiState: StateFlow<AllProductsUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.getLoggedInUser().collect { user ->
                user?.let { user ->
                    _uiState.update {
                        Log.i(TAG, "Updating products")
                        it.copy(productsList = productUseCase.getAllProductsRemote(user.id))
                    }
                }
            }
        }
    }

    fun onEvent(event: AllProductsEvent) {
        Log.i(TAG, "onEvent")
        when (event) {
            is AllProductsEvent.OnProductClicked -> {
                Log.i(TAG, event.product.toString())
            }
        }
    }
}