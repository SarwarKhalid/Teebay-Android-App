package com.example.teebay.core.data.datasource

import com.example.teebay.core.model.User

interface IUserDataSourceLocal {

    suspend fun saveUser(user: User)

    suspend fun clearUsers()

}