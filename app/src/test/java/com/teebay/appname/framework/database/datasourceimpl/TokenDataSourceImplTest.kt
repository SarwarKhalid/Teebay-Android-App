package com.teebay.appname.framework.database.datasourceimpl

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class TokenDataSourceImplTest {

    private val context: Context = mock()
    private val sharedPreferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private lateinit var tokenDataSource: TokenDataSourceImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        whenever(context.getSharedPreferences(any(), any())).thenReturn(sharedPreferences)
        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putString(any(), any())).thenReturn(editor)

        tokenDataSource = TokenDataSourceImpl(context)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveToken should save token in SharedPreferences`() {
        tokenDataSource.saveToken("test-token")

        verify(editor).putString("fcm_token", "test-token")
        verify(editor).apply()
    }

    @Test
    fun `getToken should return stored token if present`() = runTest {
        whenever(sharedPreferences.getString("fcm_token", null)).thenReturn("cached-token")

        val token = tokenDataSource.getToken()

        assertEquals("cached-token", token)
    }
}
