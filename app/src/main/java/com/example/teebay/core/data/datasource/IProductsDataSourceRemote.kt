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
}