package com.example.teebay.core.data.repository

import com.example.teebay.core.data.datasource.IUserDataSourceLocal
import com.example.teebay.core.data.datasource.IUserDataSourceRemote
import com.example.teebay.core.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDatasource: IUserDataSourceRemote,
    private val localDataSource: IUserDataSourceLocal
) {

    suspend fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        address: String,
        firebaseConsoleManagerToken: String,
        password: String
    ) = remoteDatasource.registerUser(
        email,
        firstName,
        lastName,
        address,
        firebaseConsoleManagerToken,
        password
    )

    suspend fun getUserRemote(email: String, password: String) = remoteDatasource.getUser(email, password)

    fun getUserLocal() = localDataSource.getUser()

    // Clear Users table before an insert as it should only contain 1 row(the logged in user)
    suspend fun saveUser(user: User) {
        clearUsers()
        localDataSource.saveUser(user)
    }

    suspend fun clearUsers() = localDataSource.clearUsers()
}