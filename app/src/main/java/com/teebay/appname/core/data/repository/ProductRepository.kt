package com.teebay.appname.core.data.repository

import android.content.Context
import android.net.Uri
import com.teebay.appname.core.data.datasource.IProductsDataSourceRemote
import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result
import javax.inject.Inject
import android.util.Log
import com.teebay.appname.core.model.PurchasedProduct
import com.teebay.appname.core.model.RentedProduct

private val TAG = "ProductRepository"

class ProductRepository @Inject constructor(private val productDataSourceRemote: IProductsDataSourceRemote) {

    /**
     * Returns all products available.
     */
    suspend fun getAllProducts(): Result<List<Product>> {
        Log.i(TAG,"getAllProducts")
        return productDataSourceRemote.getProducts()
    }

    /**
     * Returns all products belonging to the current user.
     */
    suspend fun getUserProducts(userId: Int): Result<List<Product>> {
        Log.i(TAG,"getUserProducts")
        val result = productDataSourceRemote.getProducts()
        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.filter { it.seller == userId })
            }

            is Result.Failure -> {
                result
            }
        }
    }

    /**
     * Returns all products not belonging to the current user.
     */
    suspend fun getAllOthersProducts(userId: Int): Result<List<Product>> {
        Log.i(TAG,"getAllOthersProducts")
        val result = productDataSourceRemote.getProducts()
        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.filter { it.seller != userId })
            }

            is Result.Failure -> {
                result
            }
        }
    }

    suspend fun deleteProduct(productId: Int) {
        Log.i(TAG,"deleteProduct")
        productDataSourceRemote.deleteProduct(productId)
    }

    suspend fun uploadProduct(context: Context, product: Product, productImageUri: Uri): Result<Product> {
        Log.i(TAG,"uploadProduct")
        return productDataSourceRemote.uploadProduct(context,product,productImageUri)
    }

    suspend fun editProduct(product: Product): Result<Product> {
        Log.i(TAG,"editProduct")
        return productDataSourceRemote.editProduct(product)
    }

    suspend fun buyProduct(buyerId: Int, productId: Int): Result<Any> {
        Log.i(TAG,"buyProduct")
        return productDataSourceRemote.buyProduct(buyerId, productId)
    }

    suspend fun rentProduct(renterId: Int, productId: Int, rentOption: String, startDate: String, endDate: String): Result<Any> {
        Log.i(TAG,"rentProduct")
        return productDataSourceRemote.rentProduct(renterId, productId, rentOption, startDate, endDate)
    }

    suspend fun getUsersPurchasedProducts(userId: Int): Result<List<PurchasedProduct>> {
        Log.i(TAG,"getPurchases")
        val result = productDataSourceRemote.getPurchases()
        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.filter { it.buyer == userId })
            }

            is Result.Failure -> result
        }
    }

    suspend fun getUsersSoldProducts(userId: Int): Result<List<PurchasedProduct>> {
        Log.i(TAG,"getPurchases")
        val result = productDataSourceRemote.getPurchases()
        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.filter { it.seller == userId })
            }

            is Result.Failure -> result
        }
    }

    suspend fun getUsersRentedProducts(userId: Int): Result<List<RentedProduct>> {
        Log.i(TAG,"getRentals")
        val result = productDataSourceRemote.getRentals()
        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.filter { it.renter == userId })
            }

            is Result.Failure -> result
        }
    }

    suspend fun getUsersLentProducts(userId: Int): Result<List<RentedProduct>> {
        Log.i(TAG,"getRentals")
        val result = productDataSourceRemote.getRentals()
        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.filter { it.seller == userId })
            }

            is Result.Failure -> result
        }
    }
}