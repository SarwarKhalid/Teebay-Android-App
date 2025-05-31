package com.example.teebay.presentation.screens.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(uiState: HomeUiState, onNavigateToLogin: () -> Unit) {
    if (uiState.isLoggedIn == true && uiState.user!=null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Welcome to Home, ${uiState.user.firstName}"
            )
        }
    } else if (uiState.isLoggedIn == false) {
        onNavigateToLogin()
    }
}