package com.teebay.appname.core.data.repository

import com.teebay.appname.core.data.datasource.IUserDataSourceLocal
import com.teebay.appname.core.data.datasource.IUserDataSourceRemote
import com.teebay.appname.core.model.Result
import com.teebay.appname.core.model.User
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class UserRepositoryTest {

    private lateinit var remote: IUserDataSourceRemote
    private lateinit var local: IUserDataSourceLocal
    private lateinit var repository: UserRepository

    private val testUser = User(
        id = 1,
        email = "test@example.com",
        firstName = "Test",
        lastName = "User",
        address = "123 Test St",
        firebaseConsoleManagerToken = "token",
        password = "pass",
        dateJoined = "2024-01-01T00:00:00Z"
    )

    @Before
    fun setup() {
        remote = mock()
        local = mock()
        repository = UserRepository(remote, local)
    }

    @Test
    fun `registerUser delegates to remote`() = runTest {
        whenever(remote.registerUser(any(), any(), any(), any(), any(), any()))
            .thenReturn(Result.Success(testUser))

        val result = repository.registerUser(
            email = testUser.email,
            firstName = testUser.firstName,
            lastName = testUser.lastName,
            address = testUser.address,
            firebaseConsoleManagerToken = testUser.firebaseConsoleManagerToken,
            password = testUser.password
        )

        assertEquals(Result.Success(testUser), result)
        verify(remote).registerUser(
            testUser.email,
            testUser.firstName,
            testUser.lastName,
            testUser.address,
            testUser.firebaseConsoleManagerToken,
            testUser.password
        )
    }

    @Test
    fun `getUserRemote calls remote`() = runTest {
        whenever(remote.getUser(any(), any()))
            .thenReturn(Result.Success(testUser))

        val result = repository.getUserRemote("test@example.com", "pass")

        assertEquals(Result.Success(testUser), result)
        verify(remote).getUser("test@example.com", "pass")
    }

    @Test
    fun `getUserLocal returns flow from local`() {
        val expectedFlow = flowOf(testUser)
        whenever(local.getUser()).thenReturn(expectedFlow)

        val result = repository.getUserLocal()

        assertSame(expectedFlow, result)
    }

    @Test
    fun `saveUser clears local DB, saves user, and caches`() = runTest {
        repository.saveUser(testUser)

        verify(local).clearUsers()
        verify(local).saveUser(testUser)
        assertEquals(testUser, repository.getCachedUser())
    }

    @Test
    fun `clearUsers clears cache and local DB`() = runTest {
        repository.saveUserToCache(testUser)

        repository.clearUsers()

        assertNull(repository.getCachedUser())
        verify(local).clearUsers()
    }

    @Test
    fun `saveUserToCache sets cached user`() {
        repository.saveUserToCache(testUser)
        assertEquals(testUser, repository.getCachedUser())
    }

    @Test
    fun `getCachedUser returns null by default`() {
        assertNull(repository.getCachedUser())
    }
}
