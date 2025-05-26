package com.example.teebay.core.data.datasource

import com.example.teebay.core.model.Result
import com.example.teebay.core.model.User

interface IUserDataSourceRemote {

    suspend fun getUser(email: String, password: String): Result<User>
}