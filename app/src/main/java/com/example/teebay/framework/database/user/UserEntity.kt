package com.example.teebay.framework.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey

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