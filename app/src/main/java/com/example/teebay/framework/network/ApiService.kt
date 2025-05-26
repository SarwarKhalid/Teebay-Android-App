package com.example.teebay.framework.network

import com.example.teebay.framework.network.request.LoginRequest
import com.example.teebay.framework.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api/users/login/")
    suspend fun loginUser(@Body body: LoginRequest): Response<LoginResponse>
}