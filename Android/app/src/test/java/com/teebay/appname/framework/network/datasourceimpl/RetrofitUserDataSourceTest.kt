package com.teebay.appname.framework.network.datasourceimpl

import com.teebay.appname.core.model.Result
import com.teebay.appname.core.model.User
import com.teebay.appname.framework.network.ApiService
import com.teebay.appname.framework.network.response.RegisterUserResponse
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class RetrofitUserDataSourceTest {

    private lateinit var apiService: ApiService
    private lateinit var dataSource: RetrofitUserDataSource

    private val testUserResponse = RegisterUserResponse(
        id = 1,
        email = "test@example.com",
        firstName = "Test",
        lastName = "User",
        address = "123 Street",
        firebaseConsoleManagerToken = "token123",
        password = "pass",
        dateJoined = "2024-01-01T00:00:00Z"
    )

    private val expectedUser = User(
        id = testUserResponse.id,
        email = testUserResponse.email,
        firstName = testUserResponse.firstName,
        lastName = testUserResponse.lastName,
        address = testUserResponse.address,
        firebaseConsoleManagerToken = testUserResponse.firebaseConsoleManagerToken,
        password = testUserResponse.password,
        dateJoined = testUserResponse.dateJoined
    )

    @Before
    fun setup() {
        apiService = mock()
        dataSource = RetrofitUserDataSource(apiService)
    }

    @Test
    fun `registerUser returns Success on valid response`() = runTest {
        val response = Response.success(testUserResponse)
        whenever(apiService.registerUser(any())).thenReturn(response)
        val result = dataSource.registerUser(
            email = "test@example.com",
            firstName = "Test",
            lastName = "User",
            address = "123 Street",
            firebaseConsoleManagerToken = "token123",
            password = "pass"
        )

        assertTrue(result is Result.Success)
        assertEquals(expectedUser, (result as Result.Success).data)
    }

    @Test
    fun `registerUser returns Failure on error response`() = runTest {
        val errorBody = ResponseBody.create("application/json".toMediaTypeOrNull(), "error")
        val errorResponse: Response<RegisterUserResponse> = Response.error(400, errorBody)

        whenever(apiService.registerUser(any())).thenReturn(errorResponse)

        val result = dataSource.registerUser(
            email = "test@example.com",
            firstName = "Test",
            lastName = "User",
            address = "123 Street",
            firebaseConsoleManagerToken = "token123",
            password = "pass"
        )

        assertTrue(result is Result.Failure)
    }
}
