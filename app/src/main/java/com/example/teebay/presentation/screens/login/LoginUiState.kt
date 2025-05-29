package com.example.teebay.presentation.screens.login

import com.example.teebay.core.model.Result

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginResult: Result<Any>? = null
)