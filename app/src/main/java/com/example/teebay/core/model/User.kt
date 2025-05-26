package com.example.teebay.core.model

data class User (
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val firebaseConsoleManagerToken: String,
    val password: String,
    val dateJoined: String
)