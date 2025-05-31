package com.example.teebay.core.data.datasource

import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result

interface IProductsDataSourceRemote {

    suspend fun getProducts(): Result<List<Product>>

    suspend fun deleteProduct(productId: Int)
}