package com.teebay.appname.core.data.repository

import android.content.Context
import android.net.Uri
import com.teebay.appname.core.data.datasource.IProductsDataSourceRemote
import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDataSourceRemote: IProductsDataSourceRemote) {

    suspend fun getProductsByUserRemote(userId: Int): Result<List<Product>> {
        val productsResult = productDataSourceRemote.getProducts()
        return when (productsResult) {
            is Result.Success -> {
                Result.Success(productsResult.data.filter { it.seller == userId })
            }

            is Result.Failure -> {
                productsResult
            }
        }
    }

    suspend fun getAllProductsRemote(userId: Int): Result<List<Product>> {
        val productsResult = productDataSourceRemote.getProducts()
        return when (productsResult) {
            is Result.Success -> {
                Result.Success(productsResult.data.filter { it.seller != userId })
            }

            is Result.Failure -> {
                productsResult
            }
        }
    }

    suspend fun deleteProduct(productId: Int) {
        productDataSourceRemote.deleteProduct(productId)
    }

    suspend fun uploadProduct(context: Context, product: Product, productImageUri: Uri): Result<Product> {
        return productDataSourceRemote.uploadProduct(context,product,productImageUri)
    }

    suspend fun editProduct(product: Product): Result<Product> {
        return productDataSourceRemote.editProduct(product)
    }

    suspend fun buyProduct(buyerId: Int, productId: Int): Result<Any> {
        return productDataSourceRemote.buyProduct(buyerId, productId)
    }

    suspend fun rentProduct(renterId: Int, productId: Int, rentOption: String, startDate: String, endDate: String): Result<Any> {
        return productDataSourceRemote.rentProduct(renterId, productId, rentOption, startDate, endDate)
    }
}