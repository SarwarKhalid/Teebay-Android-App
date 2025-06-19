package com.teebay.appname.core.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import com.teebay.appname.core.data.repository.ProductRepository
import com.teebay.appname.core.model.Product
import com.teebay.appname.core.model.Result
import javax.inject.Inject

private val TAG = "ProductUseCase"

class ProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    suspend fun getUserProducts(userId: Int) = productRepository.getUserProducts(userId)

    suspend fun getAllOthersProducts(userId: Int) = productRepository.getAllOthersProducts(userId)

    suspend fun deleteProduct(productId: Int) = productRepository.deleteProduct(productId)

    suspend fun uploadProduct(context: Context, product: Product, productImageUri: Uri): Result<Product> {
        return productRepository.uploadProduct(context, product, productImageUri)
    }

    suspend fun editProduct(product: Product): Result<Product> {
        return productRepository.editProduct(product)
    }

    suspend fun buyProduct(buyerId: Int, productId: Int): Result<Any> {
        return productRepository.buyProduct(buyerId, productId)
    }

    suspend fun rentProduct(renterId: Int, productId: Int, rentOption: String, startDate: String, endDate: String): Result<Any> {
        return productRepository.rentProduct(renterId, productId, rentOption, startDate, endDate)
    }

    /**
     * For each Product in allProducts, if that product's ID exists in purchasedProducts then adds it to a list which is returned.
     */
    suspend fun getUsersPurchasedProducts(userId: Int): Result<List<Product>> {
        val allProducts = productRepository.getAllProducts()
        if (allProducts is Result.Failure) return allProducts

        val purchasedProducts = productRepository.getUsersPurchasedProducts(userId)
        if (purchasedProducts is Result.Failure) return purchasedProducts

        (allProducts as Result.Success).data.let { allProducts ->
            (purchasedProducts as Result.Success).data.let { purchasedProducts ->
                val purchasedProductIdList = purchasedProducts.map { it.product }
                return Result.Success(
                    allProducts.filter {
                        purchasedProductIdList.contains(it.id)
                    }
                )
            }
        }
    }

    /**
     * For each Product in allProducts, if that product's ID exists in soldProducts then adds it to a list which is returned.
     */
    suspend fun getUsersSoldProducts(userId: Int): Result<List<Product>> {
        val allProducts = productRepository.getAllProducts()
        if (allProducts is Result.Failure) return allProducts

        val soldProducts = productRepository.getUsersSoldProducts(userId)
        if (soldProducts is Result.Failure) return soldProducts

        (allProducts as Result.Success).data.let { allProducts ->
            (soldProducts as Result.Success).data.let { soldProducts ->
                val soldProductIdList = soldProducts.map { it.product }
                return Result.Success(
                    allProducts.filter {
                        soldProductIdList.contains(it.id)
                    }
                )
            }
        }
    }

    /**
     * For each Product in allProducts, if that product's ID exists in rentedProducts then adds it to a list which is returned.
     */
    suspend fun getUsersRentedProducts(userId: Int): Result<List<Product>> {
        val allProducts = productRepository.getAllProducts()
        if (allProducts is Result.Failure) return allProducts

        val rentedProducts = productRepository.getUsersRentedProducts(userId)
        if (rentedProducts is Result.Failure) return rentedProducts

        (allProducts as Result.Success).data.let { allProducts ->
            (rentedProducts as Result.Success).data.let { rentedProducts ->
                val rentedProductIdList = rentedProducts.map { it.product }
                return Result.Success(
                    allProducts.filter {
                        rentedProductIdList.contains(it.id)
                    }
                )
            }
        }
    }

    /**
     * For each Product in allProducts, if that product's ID exists in lentProducts then adds it to a list which is returned.
     */
    suspend fun getUsersLentProducts(userId: Int): Result<List<Product>> {
        val allProducts = productRepository.getAllProducts()
        if (allProducts is Result.Failure) return allProducts

        val lentProducts = productRepository.getUsersLentProducts(userId)
        if (lentProducts is Result.Failure) return lentProducts

        (allProducts as Result.Success).data.let { allProducts ->
            (lentProducts as Result.Success).data.let { lentProducts ->
                val lentProductIdList = lentProducts.map { it.product }
                return Result.Success(
                    allProducts.filter {
                        lentProductIdList.contains(it.id)
                    }
                )
            }
        }
    }
}