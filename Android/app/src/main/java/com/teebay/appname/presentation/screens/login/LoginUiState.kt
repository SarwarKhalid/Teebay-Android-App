package com.teebay.appname.presentation.screens.login

import com.teebay.appname.core.model.Result

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginResult: Result<Any>? = null
)