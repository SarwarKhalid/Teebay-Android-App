package com.example.teebay.presentation.screens.signup

sealed class SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent()
    data class FirstNameChanged(val firstName: String) : SignUpEvent()
    data class LastNameChanged(val lastName: String) : SignUpEvent()
    data class AddressChanged(val address: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data object Submit : SignUpEvent()
}