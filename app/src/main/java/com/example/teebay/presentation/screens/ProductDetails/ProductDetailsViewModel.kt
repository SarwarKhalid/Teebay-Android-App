package com.example.teebay.presentation.screens.ProductDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teebay.core.domain.ProductUseCase
import com.example.teebay.core.domain.UserUseCase
import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState

    fun onEvent(event: ProductDetailsEvent) {
        when (event) {
            is ProductDetailsEvent.BuyProduct -> {
                viewModelScope.launch {
                    userUseCase.getLoggedInUser().collect { user ->
                        val product = _uiState.value.product
                        if (user != null && product != null) {
                            _uiState.update {
                                it.copy(
                                    buyProductStatus = productUseCase.buyProduct(
                                        user.id,
                                        product.id
                                    )
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(buyProductStatus = Result.Failure(message = "User or product null"))
                            }
                        }
                    }
                }
            }
        }
    }

    fun setProduct(product: Product) {
        _uiState.update { it.copy(product = product) }
    }
}