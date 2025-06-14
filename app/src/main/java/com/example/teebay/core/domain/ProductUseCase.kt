package com.example.teebay.core.domain

import android.content.Context
import android.net.Uri
import com.example.teebay.core.data.repository.ProductRepository
import com.example.teebay.core.model.Product
import com.example.teebay.core.model.Result
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    suspend fun getProductsByUser(userId: Int) = productRepository.getProductsByUserRemote(userId)

    suspend fun deleteProduct(productId: Int) = productRepository.deleteProduct(productId)

    suspend fun uploadProduct(context: Context, product: Product, productImageUri: Uri): Result<Product> {
        return productRepository.uploadProduct(context, product, productImageUri)
    }

    suspend fun editProduct(product: Product): Result<Product> {
        return productRepository.editProduct(product)
    }
}