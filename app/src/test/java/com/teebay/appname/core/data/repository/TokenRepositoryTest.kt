package com.teebay.appname.core.data.repository

import com.teebay.appname.core.data.datasource.ITokenDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class TokenRepositoryTest {

    private lateinit var tokenDataSource: ITokenDataSource
    private lateinit var repository: TokenRepository

    @Before
    fun setup() {
        tokenDataSource = mock()
        repository = TokenRepository(tokenDataSource)
    }

    @Test
    fun `saveToken calls data source`() {
        val token = "sample-token"
        repository.saveToken(token)
        verify(tokenDataSource).saveToken(token)
    }

    @Test
    fun `getToken returns token from data source`() = runTest {
        whenever(tokenDataSource.getToken()).thenReturn("abc123")

        val result = repository.getToken()

        assertEquals("abc123", result)
        verify(tokenDataSource).getToken()
    }

    @Test
    fun `refreshToken calls data source`() = runTest {
        repository.refreshToken()
        verify(tokenDataSource).refreshToken()
    }
}