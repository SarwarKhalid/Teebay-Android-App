package com.example.teebay.presentation.screens.Home

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

private val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userUseCase.getLoggedInUser().collect { user ->
                _uiState.update {
                    if(user == null) {
                        it.copy(isLoggedIn = false)
                    } else {
                        it.copy(user = user, isLoggedIn = true)
                    }
                }

            }
        }
    }

    fun onEvent(event: HomeEvent) {
        Log.i(TAG,"onEvent")
        when(event) {
            HomeEvent.OnLogout -> logout()
        }
    }

    private fun logout() {
        Log.i(TAG,"logout")
        viewModelScope.launch {
            userUseCase.logoutUser()
        }
    }
}