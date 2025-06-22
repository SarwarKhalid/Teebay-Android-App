package com.teebay.appname.presentation.screens.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teebay.appname.core.domain.ProductUseCase
import com.teebay.appname.core.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userUseCase.getLoggedInUserCached()
                _uiState.update {
                    if (user == null) {
                        it.copy(isLoggedIn = false)
                    } else {
                        updateProducts(user.id)
                        it.copy(user = user, isLoggedIn = true)
                    }
                }
        }
    }

    fun onEvent(event: HomeEvent) {
        Log.i(TAG, "onEvent")
        when (event) {
            is HomeEvent.OnLogout -> logout()
            is HomeEvent.OnDeleteProduct -> { deleteProduct(event.product.id) }
        }
    }

    private fun updateProducts(userId: Int) {
        Log.i(TAG, "updateProducts")
        viewModelScope.launch(Dispatchers.IO) {
            val products = productUseCase.getUserProducts(userId).also {
                Log.i(TAG, it.toString())
            }
            _uiState.update {
                it.copy(productsList = products)
            }
        }
    }

    private fun deleteProduct(productId: Int) {
        Log.i(TAG, "deleteProduct")
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.deleteProduct(productId)
            _uiState.value.user?.let {
                updateProducts(it.id)
            }
        }
    }

    private fun logout() {
        Log.i(TAG, "logout")
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.logoutUser()
        }
    }
}