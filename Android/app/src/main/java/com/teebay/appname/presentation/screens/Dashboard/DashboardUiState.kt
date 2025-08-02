package com.teebay.appname.presentation.screens.Dashboard

import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result

enum class DashboardTab { BOUGHT, SOLD, BORROWED, LENT }

data class DashboardUiState(
    val selectedTab: DashboardTab = DashboardTab.BOUGHT,
    val products: Result<List<Product>>? = null
)
