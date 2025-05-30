package com.example.teebay.core.domain

import com.example.teebay.core.data.repository.UserRepository
import com.example.teebay.core.model.Result
import com.example.teebay.core.model.User
import javax.inject.Inject
import kotlin.math.log

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        address: String,
        firebaseConsoleManagerToken: String,
        password: String
    ): Result<User> {
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
        val loginResponse = userRepository.getUser(email, password)
        if (loginResponse is Result.Success) {
            userRepository.saveUser(loginResponse.data)
        }
        return loginResponse
    }
}