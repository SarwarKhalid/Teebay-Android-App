package com.teebay.appname.framework.network

import com.teebay.appname.framework.network.request.LoginUserRequest
import com.teebay.appname.framework.network.request.PurchaseProductRequest
import com.teebay.appname.framework.network.request.RegisterUserRequest
import com.teebay.appname.framework.network.request.RentProductRequest
import com.teebay.appname.framework.network.response.LoginUserResponse
import com.teebay.appname.framework.network.response.ProductResponse
import com.teebay.appname.framework.network.response.PurchaseProductResponse
import com.teebay.appname.framework.network.response.RegisterUserResponse
import com.teebay.appname.framework.network.response.RentProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Multipart
    @POST("api/products/")
    suspend fun uploadProduct(
        @Part("seller") seller: RequestBody,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("categories") categories: ArrayList<RequestBody>,
        @Part product_image: MultipartBody.Part,
        @Part("purchase_price") purchasePrice: RequestBody,
        @Part("rent_price") rentPrice: RequestBody,
        @Part("rent_option") rentOption: RequestBody
    ): Response<ProductResponse>

    @Multipart
    @PATCH("api/products/{id}/")
    suspend fun editProduct(
        @Path("id") productId: Int,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("categories") categories: ArrayList<RequestBody>,
        @Part("purchase_price") purchasePrice: RequestBody,
        @Part("rent_price") rentPrice: RequestBody,
        @Part("rent_option") rentOption: RequestBody
    ): Response<ProductResponse>

    @GET("api/transactions/purchases/")
    suspend fun getPurchases(): Response<List<PurchaseProductResponse>>

    @POST("api/transactions/purchases/")
    suspend fun buyProduct(@Body body: PurchaseProductRequest): Response<PurchaseProductResponse>

    @GET("api/transactions/rentals/")
    suspend fun getRentals(): Response<List<RentProductResponse>>

    @POST("api/transactions/rentals/")
    suspend fun rentProduct(@Body body: RentProductRequest): Response<RentProductResponse>
}