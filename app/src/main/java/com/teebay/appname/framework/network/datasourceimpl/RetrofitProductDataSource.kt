package com.teebay.appname.framework.network.datasourceimpl

import android.content.Context
import android.net.Uri
import android.util.Log
import com.teebay.appname.core.data.datasource.IProductsDataSourceRemote
import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.PurchasedProduct
import com.teebay.appname.core.model.RentedProduct
import com.teebay.appname.core.model.Result
import com.teebay.appname.framework.network.ApiService
import com.teebay.appname.framework.network.NetworkUtils
import com.teebay.appname.framework.network.request.PurchaseProductRequest
import com.teebay.appname.framework.network.request.RentProductRequest
import com.teebay.appname.framework.network.response.toProduct
import com.teebay.appname.framework.network.response.toPurchasedProduct
import com.teebay.appname.framework.network.response.toRentedProduct
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

private val TAG = "RetrofitProductDataSource"

class RetrofitProductDataSource @Inject constructor(private val apiService: ApiService) :
    IProductsDataSourceRemote {

    override suspend fun getProducts(): Result<List<Product>> {
        Log.i(TAG, "getProducts")
        val response = NetworkUtils.handleApiResponse {
            apiService.getProducts()
        }
        Log.i(TAG, response.toString())
        return when (response) {
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

    override suspend fun uploadProduct(
        context: Context,
        product: Product,
        productImageUri: Uri
    ): Result<Product> {
        Log.i(TAG, "uploadProduct")
        val response = NetworkUtils.handleApiResponse {
            product.run {
                apiService.uploadProduct(
                    seller.toString().toRequestBodyPart(),
                    title.toRequestBodyPart(),
                    description.toRequestBodyPart(),
                    categories.toRequestBodyPartList(),
                    getFileFromUri(context, productImageUri).toImagePart(),
                    purchasePrice.toRequestBodyPart(),
                    rentPrice.toRequestBodyPart(),
                    rentOption.toRequestBodyPart()
                )
            }
        }
        Log.i(TAG, response.toString())
        return when (response) {
            is Result.Success -> {
                Result.Success(response.data.toProduct())
            }

            is Result.Failure -> {
                response
            }
        }

    }

    override suspend fun editProduct(
        product: Product
    ): Result<Product> {
        Log.i(TAG, "editProduct")
        val response = NetworkUtils.handleApiResponse {
            product.run {
                apiService.editProduct(
                    id,
                    title.toRequestBodyPart(),
                    description.toRequestBodyPart(),
                    categories.toRequestBodyPartList(),
                    purchasePrice.toRequestBodyPart(),
                    rentPrice.toRequestBodyPart(),
                    rentOption.toRequestBodyPart()
                )
            }
        }
        Log.i(TAG, response.toString())
        return when (response) {
            is Result.Success -> {
                Result.Success(response.data.toProduct())
            }

            is Result.Failure -> {
                response
            }
        }
    }

    override suspend fun buyProduct(buyerId: Int, productId: Int): Result<Any> {
        Log.i(TAG, "buyProduct")
        val response = NetworkUtils.handleApiResponse {
            apiService.buyProduct(PurchaseProductRequest(buyerId, productId))
        }
        return response.also {
            Log.i(TAG, it.toString())
        }
    }

    override suspend fun rentProduct(
        renterId: Int,
        productId: Int,
        rentOption: String,
        startDate: String,
        endDate: String
    ): Result<Any> {
        Log.i(TAG, "rentProduct")
        val response = NetworkUtils.handleApiResponse {
            apiService.rentProduct(
                RentProductRequest(
                    renterId,
                    productId,
                    rentOption,
                    startDate,
                    endDate
                )
            )
        }
        return response.also {
            Log.i(TAG, it.toString())
        }
    }

    override suspend fun getPurchase(transactionId: Int): Result<PurchasedProduct> {
        Log.i(TAG, "getPurchase")
        val response = NetworkUtils.handleApiResponse {
            apiService.getPurchase(transactionId)
        }
        Log.i(TAG, response.toString())
        return when (response) {
            is Result.Success -> {
                Result.Success(response.data.toPurchasedProduct())
            }

            is Result.Failure -> {
                response
            }
        }
    }

    override suspend fun getPurchases(): Result<List<PurchasedProduct>> {
        Log.i(TAG, "getPurchases")
        val response = NetworkUtils.handleApiResponse {
            apiService.getPurchases()
        }
        Log.i(TAG, response.toString())
        return when (response) {
            is Result.Success -> {
                Result.Success(response.data.map { it.toPurchasedProduct() })
            }

            is Result.Failure -> {
                response
            }
        }
    }

    override suspend fun getRental(transactionId: Int): Result<RentedProduct> {
        Log.i(TAG, "getRental")
        val response = NetworkUtils.handleApiResponse {
            apiService.getRental(transactionId)
        }
        Log.i(TAG, response.toString())
        return when (response) {
            is Result.Success -> {
                Result.Success(response.data.toRentedProduct())
            }

            is Result.Failure -> {
                response
            }
        }
    }

    override suspend fun getRentals(): Result<List<RentedProduct>> {
        Log.i(TAG, "getRentals")
        val response = NetworkUtils.handleApiResponse {
            apiService.getRentals()
        }
        Log.i(TAG, response.toString())
        return when (response) {
            is Result.Success -> {
                Result.Success(response.data.map { it.toRentedProduct() })
            }

            is Result.Failure -> {
                response
            }
        }
    }
}

private fun getFileFromUri(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
    tempFile.outputStream().use { output ->
        inputStream?.copyTo(output)
    }
    return tempFile
}

private fun String.toRequestBodyPart(): RequestBody =
    this.toRequestBody("text/plain".toMediaTypeOrNull())

private fun List<String>.toRequestBodyPartList(): ArrayList<RequestBody> =
    ArrayList(this.map { it.toRequestBody("text/plain".toMediaTypeOrNull()) })

private fun File.toImagePart(fieldName: String = "product_image"): MultipartBody.Part {
    val imageRequest = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(fieldName, this.name, imageRequest)
}