package com.teebay.appname.framework.network.response

import com.google.gson.annotations.SerializedName
import com.teebay.appname.core.model.User

data class LoginUserResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("user")
    val user: LoginResponseUserData
)

data class LoginResponseUserData(
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

fun LoginUserResponse.toUser(): User {
    return user.run {
        User(
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
}