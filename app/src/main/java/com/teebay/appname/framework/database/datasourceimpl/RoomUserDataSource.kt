package com.teebay.appname.framework.database.datasourceimpl

import android.util.Log
import com.teebay.appname.core.data.datasource.IUserDataSourceLocal
import com.teebay.appname.core.model.User
import com.teebay.appname.framework.database.user.UserDao
import com.teebay.appname.framework.database.user.UserEntity
import com.teebay.appname.framework.database.user.toUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomUserDataSource @Inject constructor(private val userDao: UserDao): IUserDataSourceLocal {

    private val TAG = "RoomUserDataSource"

    override fun getUser(): Flow<User?> {
        Log.i(TAG,"getUser")
        return userDao.getUser().map { user ->
            user?.let {
                user.toUser()
            }
        }
    }

    override suspend fun saveUser(user: User) {
        Log.i(TAG,"saveUser")
        runCatching {
            user.run {
                UserEntity(
                    id = id,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    address = address,
                    firebaseConsoleManagerToken = firebaseConsoleManagerToken,
                    password = password,
                    dateJoined = dateJoined
                ).also {
                    userDao.saveUser(it)
                    Log.i(TAG,"User saved")
                }
            }
        }.onFailure { error: Throwable ->
            Log.i(TAG,error.toString())
        }
    }

    override suspend fun clearUsers() {
        Log.i(TAG,"clearUsers")
        runCatching {
            userDao.clearUsers()
        }.onFailure { error: Throwable ->
            Log.i(TAG,error.toString())
        }
    }
}