package com.example.teebay.core.data.repository

import com.example.teebay.core.data.datasource.IProductsDataSourceRemote
import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result
import javax.inject.Inject

class ProductRepository @Inject constructor(private val remoteDataSourceRemote: IProductsDataSourceRemote) {

    suspend fun getProductsByUserRemote(userId: Int): Result<List<Product>> {
        val productsResult = remoteDataSourceRemote.getProducts()
        return when (productsResult) {
            is Result.Success -> {
                Result.Success(productsResult.data.filter { it.seller == userId })
            }

            is Result.Failure -> {
                productsResult
            }
        }
    }

    suspend fun deleteProduct(productId: Int) {
        remoteDataSourceRemote.deleteProduct(productId)
    }
}