package com.teebay.appname.framework.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.teebay.appname.core.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val firebaseConsoleManagerToken: String,
    val password: String,
    val dateJoined: String
)

fun UserEntity.toUser(): User {
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