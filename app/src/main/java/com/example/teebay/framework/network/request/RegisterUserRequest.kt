package com.example.teebay.framework.network.request

import com.google.gson.annotations.SerializedName

data class RegisterUserRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("firebase_console_manager_token")
    val firebaseConsoleManagerToken: String,
    @SerializedName("password")
    val password: String,
)
