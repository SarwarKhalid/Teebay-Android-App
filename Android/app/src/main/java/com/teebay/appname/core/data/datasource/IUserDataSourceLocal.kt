package com.teebay.appname.core.data.datasource

import com.teebay.appname.core.model.User
import kotlinx.coroutines.flow.Flow

interface IUserDataSourceLocal {

    fun getUser(): Flow<User?>

    suspend fun saveUser(user: User)

    suspend fun clearUsers()

}