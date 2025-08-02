package com.teebay.appname.core.domain

import android.util.Log
import com.teebay.appname.core.data.repository.UserRepository
import com.teebay.appname.core.model.Result
import com.teebay.appname.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private val TAG = "UserUseCase"

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        address: String,
        firebaseConsoleManagerToken: String,
        password: String
    ): Result<User> {
        Log.i(TAG,"registerUser")
        val registerUserResponse = userRepository.registerUser(
            email,
            firstName,
            lastName,
            address,
            firebaseConsoleManagerToken,
            password
        )
        if(registerUserResponse is Result.Success) {
            userRepository.saveUser(registerUserResponse.data)
        }
        return registerUserResponse
    }

    suspend fun loginUser(email: String, password: String): Result<User> {

        val loginResponse = userRepository.getUserRemote(email, password)
        if (loginResponse is Result.Success) {
            userRepository.saveUser(loginResponse.data)
        }
        return loginResponse
    }

    suspend fun logoutUser() {
        Log.i(TAG,"logOutUser")
        userRepository.clearUsers()
    }

    /**
     * Retrieves logged in user from database
     */
    fun getLoggedInUser(): Flow<User?> {
        Log.i(TAG,"getLoggedInUser")
        return userRepository.getUserLocal()
    }

    /**
     * Retrieves logged in user from cache
     */
    fun getLoggedInUserCached() = userRepository.getCachedUser()

    fun saveUserToCache(user: User) {
        userRepository.saveUserToCache(user)
    }
}