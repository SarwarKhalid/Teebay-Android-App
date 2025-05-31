package com.example.teebay.framework.network.datasourceimpl

import android.util.Log
import com.example.teebay.core.data.datasource.IProductsDataSourceRemote
import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result
import com.example.teebay.framework.network.ApiService
import com.example.teebay.framework.network.NetworkUtils
import com.example.teebay.framework.network.response.toProduct
import jakarta.inject.Inject

private val TAG = "RetrofitProductDataSource"

class RetrofitProductDataSource @Inject constructor(private val apiService: ApiService) :
    IProductsDataSourceRemote {
    override suspend fun getProducts(): Result<List<Product>> {
        Log.i(TAG, "getProducts")

        val response = NetworkUtils.handleApiResponse {
            apiService.getProducts()
        }
        Log.i(TAG,response.toString())
        return when(response) {
            is Result.Success -> {
                Result.Success(response.data.map { it.toProduct() })
            }
            is Result.Failure -> {
                response
            }
        }

    }

    override suspend fun deleteProduct(productId: Int) {
        Log.i(TAG, "deleteProduct")
        apiService.deleteProduct(productId)
    }
}