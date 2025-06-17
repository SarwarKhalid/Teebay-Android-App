package com.example.teebay.framework.network.datasourceimpl

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.teebay.core.data.datasource.IProductsDataSourceRemote
import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result
import com.example.teebay.framework.network.ApiService
import com.example.teebay.framework.network.NetworkUtils
import com.example.teebay.framework.network.request.PurchaseProductRequest
import com.example.teebay.framework.network.response.toProduct
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