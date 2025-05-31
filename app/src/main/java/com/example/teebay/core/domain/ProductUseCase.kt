package com.example.teebay.core.domain

import com.example.teebay.core.data.repository.ProductRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    suspend fun getProductsByUser(userId: Int) = productRepository.getProductsByUserRemote(userId)

    suspend fun deleteProduct(productId: Int) = productRepository.deleteProduct(productId)
}