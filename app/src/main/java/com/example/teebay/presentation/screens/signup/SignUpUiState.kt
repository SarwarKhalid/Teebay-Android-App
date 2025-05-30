package com.example.teebay.presentation.screens.signup

import com.example.teebay.core.model.Result

data class SignUpUiState(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val password: String = "",
    val signupResult: Result<Any>? = null
)
