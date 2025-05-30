package com.example.teebay.presentation.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teebay.core.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        Log.i(TAG,"onEvent")
        when (event) {
            is LoginEvent.EmailChanged -> _uiState.update { it.copy(email = event.email) }
            is LoginEvent.PasswordChanged -> _uiState.update { it.copy(password = event.password) }
            is LoginEvent.Submit -> login()
        }
    }

    private fun login() {
        Log.i(TAG,"login")
        viewModelScope.launch {
            _uiState.update {
                it.copy(loginResult = userUseCase.loginUser(it.email, it.password))
            }
        }
    }
}