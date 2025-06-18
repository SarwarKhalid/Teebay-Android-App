package com.teebay.appname.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.teebay.appname.presentation.screens.signup.SignUpScreen
import com.teebay.appname.presentation.screens.signup.SignUpViewModel
import kotlinx.serialization.Serializable

@Serializable
object SignupRoute

fun NavGraphBuilder.signupDestination(onNavigateToHome: () -> Unit) {
    composable<SignupRoute> {
        val viewModel: SignUpViewModel = hiltViewModel()
        val signupUiState by viewModel.uiState.collectAsStateWithLifecycle()
        SignUpScreen(signupUiState,viewModel::onEvent, onNavigateToHome)
    }
}

fun NavController.navigateToSignup() {
    navigate(route = SignupRoute)
}