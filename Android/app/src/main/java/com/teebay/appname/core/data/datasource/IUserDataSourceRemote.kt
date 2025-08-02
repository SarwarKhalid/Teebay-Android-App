package com.teebay.appname.core.data.datasource

import com.teebay.appname.core.model.Result
import com.teebay.appname.core.model.User

interface IUserDataSourceRemote {

    suspend fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        address: String,
        firebaseConsoleManagerToken: String,
        password: String
    ): Result<User>

    suspend fun getUser(email: String, password: String): Result<User>
}