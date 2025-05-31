package com.example.teebay.framework.network

import com.example.teebay.framework.network.request.LoginUserRequest
import com.example.teebay.framework.network.request.RegisterUserRequest
import com.example.teebay.framework.network.response.LoginUserResponse
import com.example.teebay.framework.network.response.ProductResponse
import com.example.teebay.framework.network.response.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //Users API

    @POST("api/users/register/")
    suspend fun registerUser(@Body body: RegisterUserRequest): Response<RegisterUserResponse>

    @POST("api/users/login/")
    suspend fun loginUser(@Body body: LoginUserRequest): Response<LoginUserResponse>


    //Products API

    @GET("/api/products/")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @DELETE("api/products/{id}/")
    suspend fun deleteProduct(@Path("id") productId: Int)
}