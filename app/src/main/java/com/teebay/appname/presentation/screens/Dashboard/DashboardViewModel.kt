package com.teebay.appname.presentation.screens.Dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teebay.appname.core.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ProductRepository
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
//            val result = when (tab) {
//                ProductHistoryTab.BOUGHT -> repository.getBoughtProducts()
//                ProductHistoryTab.SOLD -> repository.getSoldProducts()
//                ProductHistoryTab.BORROWED -> repository.getBorrowedProducts()
//                ProductHistoryTab.LENT -> repository.getLentProducts()
//            }
//            _uiState.update { it.copy(products = result) }
        }
    }
}