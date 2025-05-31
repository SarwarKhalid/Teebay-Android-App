package com.example.teebay.presentation.screens.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.teebay.presentation.components.DrawerScaffold

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToMyProducts: () -> Unit,
) {
    if (uiState.isLoggedIn == true) {
        DrawerScaffold(
            onMyProductsClick = onNavigateToMyProducts,
            onLogout = {
                onEvent(HomeEvent.OnLogout)
                onNavigateToLogin()
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Welcome to Home, ${uiState.user?.firstName}")
            }
        }
    } else if (uiState.isLoggedIn == false) {
        onNavigateToLogin()
    }
}