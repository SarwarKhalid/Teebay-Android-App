package com.example.teebay.core.data.datasource

import android.content.Context
import android.net.Uri
import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result

interface IProductsDataSourceRemote {

    suspend fun getProducts(): Result<List<Product>>

    suspend fun deleteProduct(productId: Int)

    suspend fun uploadProduct(context: Context, product: Product, productImageUri: Uri): Result<Product>

    suspend fun editProduct(product: Product): Result<Product>

    suspend fun buyProduct(buyerId: Int, productId: Int): Result<Any>

    suspend fun rentProduct(renterId: Int, productId: Int, rentOption: String, startDate: String, endDate: String): Result<Any>
}