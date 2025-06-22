package com.teebay.appname.core.data.repository

import android.content.Context
import android.net.Uri
import com.teebay.appname.core.data.datasource.IProductsDataSourceRemote
import com.teebay.appname.core.model.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class ProductRepositoryTest {

    private lateinit var remote: IProductsDataSourceRemote
    private lateinit var repository: ProductRepository

    private val product1 = Product(
        id = 1,
        seller = 5,
        title = "Laptop",
        description = "",
        categories = listOf(),
        productImage = "",
        purchasePrice = "500",
        rentPrice = "50",
        rentOption = "day",
        datePosted = ""
    )
    private val product2 = Product(
        id = 2,
        seller = 10,
        title = "Phone",
        description = "",
        categories = listOf(),
        productImage = "",
        purchasePrice = "200",
        rentPrice = "20",
        rentOption = "hour",
        datePosted = ""
    )

    private val purchased = PurchasedProduct(id = 1, product = 1, buyer = 5, seller = 10, purchaseDate = "")
    private val rented = RentedProduct(
        id = 2,
        product = 2,
        renter = 5,
        seller = 10,
        startDate = "",
        endDate = "",
        rentOption = "day",
        totalPrice = "1",
        rentDate = "",
    )

    @Before
    fun setup() {
        remote = mock()
        repository = ProductRepository(remote)
    }

    @Test
    fun `getAllProducts returns result from remote`() = runTest {
        whenever(remote.getProducts()).thenReturn(Result.Success(listOf(product1, product2)))

        val result = repository.getAllProducts()

        assertEquals(Result.Success(listOf(product1, product2)), result)
    }

    @Test
    fun `getUserProducts filters by seller ID`() = runTest {
        whenever(remote.getProducts()).thenReturn(Result.Success(listOf(product1, product2)))

        val result = repository.getUserProducts(userId = 5)

        assertEquals(Result.Success(listOf(product1)), result)
    }

    @Test
    fun `getAllOthersProducts filters out seller ID`() = runTest {
        whenever(remote.getProducts()).thenReturn(Result.Success(listOf(product1, product2)))

        val result = repository.getAllOthersProducts(userId = 5)

        assertEquals(Result.Success(listOf(product2)), result)
    }

    @Test
    fun `deleteProduct delegates to remote`() = runTest {
        repository.deleteProduct(1)
        verify(remote).deleteProduct(1)
    }

    @Test
    fun `uploadProduct delegates to remote`() = runTest {
        val context = mock<Context>()
        val uri = mock<Uri>()
        val result = Result.Success(product1)
        whenever(remote.uploadProduct(context, product1, uri)).thenReturn(result)

        val actual = repository.uploadProduct(context, product1, uri)
        assertEquals(result, actual)
    }

    @Test
    fun `editProduct delegates to remote`() = runTest {
        whenever(remote.editProduct(product1)).thenReturn(Result.Success(product1))

        val result = repository.editProduct(product1)
        assertEquals(Result.Success(product1), result)
    }

    @Test
    fun `buyProduct delegates to remote`() = runTest {
        whenever(remote.buyProduct(1, 2)).thenReturn(Result.Success("OK"))

        val result = repository.buyProduct(1, 2)
        assertEquals(Result.Success("OK"), result)
    }

    @Test
    fun `rentProduct delegates to remote`() = runTest {
        whenever(
            remote.rentProduct(
                1,
                2,
                "day",
                "2024-01-01",
                "2024-01-05"
            )
        ).thenReturn(Result.Success("OK"))

        val result = repository.rentProduct(1, 2, "day", "2024-01-01", "2024-01-05")
        assertEquals(Result.Success("OK"), result)
    }

    @Test
    fun `getPurchasedProduct delegates to remote`() = runTest {
        whenever(remote.getPurchase(1)).thenReturn(Result.Success(purchased))

        val result = repository.getPurchasedProduct(1)
        assertEquals(Result.Success(purchased), result)
    }

    @Test
    fun `getUsersPurchasedProducts filters by buyer`() = runTest {
        whenever(remote.getPurchases()).thenReturn(Result.Success(listOf(purchased.copy(buyer = 5), purchased.copy(buyer = 2))))

        val result = repository.getUsersPurchasedProducts(5)
        assertEquals(Result.Success(listOf(purchased.copy(buyer = 5))), result)
    }

    @Test
    fun `getUsersSoldProducts filters by seller`() = runTest {
        whenever(remote.getPurchases()).thenReturn(Result.Success(listOf(purchased.copy(seller = 10), purchased.copy(seller = 5))))

        val result = repository.getUsersSoldProducts(5)
        assertEquals(Result.Success(listOf(purchased.copy(seller = 5))), result)
    }

    @Test
    fun `getRentedProduct delegates to remote`() = runTest {
        whenever(remote.getRental(88)).thenReturn(Result.Success(rented))

        val result = repository.getRentedProduct(88)
        assertEquals(Result.Success(rented), result)
    }

    @Test
    fun `getUsersRentedProducts filters by renter`() = runTest {
        whenever(remote.getRentals()).thenReturn(Result.Success(listOf(rented.copy(renter = 5), rented.copy(renter = 3))))

        val result = repository.getUsersRentedProducts(5)
        assertEquals(Result.Success(listOf(rented.copy(renter = 5))), result)
    }

    @Test
    fun `getUsersLentProducts filters by seller`() = runTest {
        whenever(remote.getRentals()).thenReturn(Result.Success(listOf(rented.copy(seller = 5), rented.copy(seller = 3))))

        val result = repository.getUsersLentProducts(5)
        assertEquals(Result.Success(listOf(rented.copy(seller = 5))), result)
    }

    @Test
    fun `get methods return failure if remote fails`() = runTest {
        val error = Result.Failure(500, "Server error")

        whenever(remote.getProducts()).thenReturn(error)
        whenever(remote.getPurchases()).thenReturn(error)
        whenever(remote.getRentals()).thenReturn(error)

        assertEquals(error, repository.getAllProducts())
        assertEquals(error, repository.getUserProducts(1))
        assertEquals(error, repository.getAllOthersProducts(1))
        assertEquals(error, repository.getUsersPurchasedProducts(1))
        assertEquals(error, repository.getUsersSoldProducts(1))
        assertEquals(error, repository.getUsersRentedProducts(1))
        assertEquals(error, repository.getUsersLentProducts(1))
    }
}
