package com.teebay.appname.core.data.datasource

import android.content.Context
import android.net.Uri
import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.PurchasedProduct
import com.teebay.appname.core.model.RentedProduct
import com.teebay.appname.core.model.Result

interface IProductsDataSourceRemote {

    suspend fun getProducts(): Result<List<Product>>

    suspend fun deleteProduct(productId: Int)

    suspend fun uploadProduct(context: Context, product: Product, productImageUri: Uri): Result<Product>

    suspend fun editProduct(product: Product): Result<Product>

    suspend fun buyProduct(buyerId: Int, productId: Int): Result<Any>

    suspend fun rentProduct(renterId: Int, productId: Int, rentOption: String, startDate: String, endDate: String): Result<Any>

    suspend fun getPurchases(): Result<List<PurchasedProduct>>

    suspend fun getRentals(): Result<List<RentedProduct>>
}