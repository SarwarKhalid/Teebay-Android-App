package com.example.teebay.core.data.repository

import com.example.teebay.core.data.datasource.IUserDataSourceLocal
import com.example.teebay.core.data.datasource.IUserDataSourceRemote
import com.example.teebay.core.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDatasource: IUserDataSourceRemote,
    private val localDataSource: IUserDataSourceLocal
) {

    suspend fun getUser(email: String, password: String) = remoteDatasource.getUser(email, password)
    suspend fun saveUser(user: User) = localDataSource.saveUser(user)
}