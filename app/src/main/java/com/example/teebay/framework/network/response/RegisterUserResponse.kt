package com.example.teebay.framework.network.response

import com.example.teebay.core.model.User
import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("id")
    val id: Int,
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
    @SerializedName("date_joined")
    val dateJoined: String
)

fun RegisterUserResponse.toUser(): User {
    return User(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        address = address,
        firebaseConsoleManagerToken = firebaseConsoleManagerToken,
        password = password,
        dateJoined = dateJoined
    )
}