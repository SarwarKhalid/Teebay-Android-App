package com.teebay.appname.presentation.screens.Dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teebay.appname.core.domain.ProductUseCase
import com.teebay.appname.core.domain.UserUseCase
import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        loadProductsForTab(DashboardTab.BOUGHT)
    }

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.OnTabSelected -> {
                _uiState.update { it.copy(selectedTab = event.tab, products = null) }
                loadProductsForTab(event.tab)
            }
        }
    }

    private fun loadProductsForTab(tab: DashboardTab) {
        viewModelScope.launch {
            userUseCase.getLoggedInUser().collect { user ->
              if(user != null) {
                  var productList: Result<List<Product>>? = null
                  when(tab) {
                      DashboardTab.BOUGHT -> productList = productUseCase.getUsersPurchasedProducts(user.id)
                      DashboardTab.SOLD ->  productList = productUseCase.getUsersSoldProducts(user.id)
                      DashboardTab.BORROWED ->  productList = productUseCase.getUsersRentedProducts(user.id)
                      DashboardTab.LENT ->  productList = productUseCase.getUsersLentProducts(user.id)
                  }
                  _uiState.update { it.copy(products = productList) }
              } else {
                  _uiState.update { it.copy(products = Result.Failure()) }
              }
            }

        }
    }
}