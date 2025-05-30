package com.example.teebay.framework.network

import com.example.teebay.framework.network.request.LoginUserRequest
import com.example.teebay.framework.network.request.RegisterUserRequest
import com.example.teebay.framework.network.response.LoginUserResponse
import com.example.teebay.framework.network.response.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/users/register/")
    suspend fun registerUser(@Body body: RegisterUserRequest): Response<RegisterUserResponse>

    @POST("api/users/login/")
    suspend fun loginUser(@Body body: LoginUserRequest): Response<LoginUserResponse>
}