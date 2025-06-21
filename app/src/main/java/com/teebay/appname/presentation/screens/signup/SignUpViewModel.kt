package com.teebay.appname.presentation.screens.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teebay.appname.core.domain.TokenUseCase
import com.teebay.appname.core.domain.UserUseCase
import com.teebay.appname.core.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "SignUpViewModel"

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val tokenUseCase: TokenUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> _uiState.update { it.copy(email = event.email) }
            is SignUpEvent.FirstNameChanged -> _uiState.update { it.copy(firstName = event.firstName) }
            is SignUpEvent.LastNameChanged -> _uiState.update { it.copy(lastName = event.lastName) }
            is SignUpEvent.AddressChanged -> _uiState.update { it.copy(address = event.address) }
            is SignUpEvent.PasswordChanged -> _uiState.update { it.copy(password = event.password) }
            SignUpEvent.Submit -> signup()
        }
    }

    private fun signup() {
        Log.i(TAG, "signup")
        Log.i(TAG, _uiState.value.toString())
        viewModelScope.launch {
            val token = tokenUseCase.getToken()
            if(token.isNullOrBlank()) {
                _uiState.update { it.copy(signupResult = Result.Failure()) }
            } else {
                _uiState.update {
                    it.copy(
                        signupResult = userUseCase.registerUser(
                            it.email,
                            it.firstName,
                            it.lastName,
                            it.address,
                            token,
                            it.password
                        )
                    )
                }
            }
        }
    }
}