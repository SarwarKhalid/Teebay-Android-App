package com.example.teebay.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.teebay.presentation.screens.login.LoginScreen
import com.example.teebay.presentation.screens.login.LoginViewModel
import kotlinx.serialization.Serializable


@Serializable
object LoginRoute

fun NavGraphBuilder.loginDestination(onNavigateToHome: () -> Unit) {
    composable<LoginRoute> {
        val viewModel: LoginViewModel = hiltViewModel()
        val loginUiState by viewModel.uiState.collectAsStateWithLifecycle()
        LoginScreen(loginUiState,viewModel::onEvent, onNavigateToHome)
    }
}

fun NavController.navigateToLogin() {
    navigate(route = LoginRoute)
}