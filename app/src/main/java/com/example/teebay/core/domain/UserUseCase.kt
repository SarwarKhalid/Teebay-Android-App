package com.example.teebay.core.domain

import android.util.Log
import com.example.teebay.core.data.repository.UserRepository
import com.example.teebay.core.model.Result
import com.example.teebay.core.model.User
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
        Log.i(TAG,"loginUser")
        val loginResponse = userRepository.getUserRemote(email, password)
        if (loginResponse is Result.Success) {
            userRepository.saveUser(loginResponse.data)
        }
        return loginResponse
    }

    fun getLoggedInUser(): Flow<User?> {
        Log.i(TAG,"getLoggedInUser")
        return userRepository.getUserLocal()
    }
}