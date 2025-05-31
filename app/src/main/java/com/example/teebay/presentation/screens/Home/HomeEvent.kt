package com.example.teebay.presentation.screens.Home


sealed class HomeEvent {
    data object OnLogout : HomeEvent()
}