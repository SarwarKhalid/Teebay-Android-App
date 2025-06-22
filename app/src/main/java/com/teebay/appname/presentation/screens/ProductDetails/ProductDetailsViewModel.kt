package com.teebay.appname.presentation.screens.ProductDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teebay.appname.core.domain.ProductUseCase
import com.teebay.appname.core.domain.UserUseCase
import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result.*
import com.teebay.appname.framework.Util.TimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
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
                    userUseCase.getLoggedInUserCached()?.let { user ->
                        val product = _uiState.value.product
                        if (product != null) {
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
                                it.copy(buyProductStatus = Failure(message = "User or product null"))
                            }
                        }
                    }
                }
            }

            is ProductDetailsEvent.ShowConfirmPurchaseDialog -> {
                _uiState.update { it.copy(confirmPurchaseDialog = true) }
            }

            is ProductDetailsEvent.DismissConfirmPurchaseDialog -> {
                _uiState.update { it.copy(confirmPurchaseDialog = false) }
            }

            is ProductDetailsEvent.ShowRentDialog -> {
                _uiState.update { it.copy(showRentDialog = true) }
            }

            is ProductDetailsEvent.DismissRentDialog -> {
                _uiState.update { it.copy(showRentDialog = false) }
            }

            is ProductDetailsEvent.SetRentDateTimes -> {
                val duration = Duration.between(event.start, event.end)
                 _uiState.value.product?.let { product ->
                     val price: Float = product.rentPrice.toFloat()
                     val option = product.rentOption
                     val total = when (option.lowercase()) {
                         "day" -> duration.toDays().coerceAtLeast(1) * price
                         "hour" -> duration.toHours().coerceAtLeast(1) * price
                         else -> 0.0
                     }

                     _uiState.update {
                         it.copy(
                             rentStartDateTime = event.start,
                             rentEndDateTime = event.end,
                             totalRentCost = total.toString()
                         )
                     }
                 }
            }

            is ProductDetailsEvent.ConfirmRent -> {
                // Implement rent logic here
                val product = _uiState.value.product
                val startDateTime = _uiState.value.rentStartDateTime.toString()
                val endDateTime = _uiState.value.rentEndDateTime.toString()
                Log.i("RENT", "Confirming rent: $startDateTime to $endDateTime for product ${product?.title}")
                viewModelScope.launch {
                    userUseCase.getLoggedInUserCached()?.let { user ->
                        if (product != null) {
                            _uiState.update {
                                it.copy(
                                    rentProductStatus = productUseCase.rentProduct(
                                        user.id,
                                        product.id,
                                        product.rentOption,
                                        TimeUtil.convertLocalToUtcString(startDateTime),
                                        TimeUtil.convertLocalToUtcString(endDateTime)
                                    )
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(buyProductStatus = Failure(message = "User or product null"))
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