package com.teebay.appname.core.data.repository

import com.teebay.appname.core.data.datasource.IUserDataSourceLocal
import com.teebay.appname.core.data.datasource.IUserDataSourceRemote
import com.teebay.appname.core.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDatasource: IUserDataSourceRemote,
    private val localDataSource: IUserDataSourceLocal
) {

    // In-memory cache
    private var cachedUser: User? = null

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

    fun getCachedUser() = cachedUser

    // Clear Users table before an insert as it should only contain 1 row(the logged in user)
    suspend fun saveUser(user: User) {
        clearUsers()
        localDataSource.saveUser(user)
        cachedUser = user
    }

    suspend fun clearUsers() {
        cachedUser = null
        localDataSource.clearUsers()
    }
}