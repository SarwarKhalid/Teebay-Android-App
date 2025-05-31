package com.example.teebay.core.data.datasource

import com.example.teebay.core.model.User
import kotlinx.coroutines.flow.Flow

interface IUserDataSourceLocal {

    fun getUser(): Flow<User?>

    suspend fun saveUser(user: User)

    suspend fun clearUsers()

}