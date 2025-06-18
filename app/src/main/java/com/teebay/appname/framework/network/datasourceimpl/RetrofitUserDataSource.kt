package com.teebay.appname.framework.network.datasourceimpl

import android.util.Log
import com.teebay.appname.core.data.datasource.IUserDataSourceRemote
import com.teebay.appname.framework.network.ApiService
import com.teebay.appname.framework.network.NetworkUtils
import com.teebay.appname.framework.network.request.LoginUserRequest
import com.teebay.appname.core.model.Result
import com.teebay.appname.core.model.User
import com.teebay.appname.framework.network.request.RegisterUserRequest
import com.teebay.appname.framework.network.response.toUser
import jakarta.inject.Inject

class RetrofitUserDataSource @Inject constructor(private val apiService: ApiService) :
    IUserDataSourceRemote {

    private val TAG = "RetrofitUserDatasource"
    override suspend fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        address: String,
        firebaseConsoleManagerToken: String,
        password: String
    ): Result<User> {
        Log.i(TAG, "registerUser")
        val response = NetworkUtils.handleApiResponse {
            apiService.registerUser(RegisterUserRequest(email, firstName, lastName, address, firebaseConsoleManagerToken, password))
        }
        Log.i(TAG,response.toString())
        return when (response) {
            is Result.Success -> {
                Result.Success(response.data.toUser())
            }

            is Result.Failure -> {
                response
            }
        }
    }

    override suspend fun getUser(email: String, password: String): Result<User> {
        Log.i(TAG, "loginUser")
        val response = NetworkUtils.handleApiResponse {
            apiService.loginUser(LoginUserRequest(email, password))
        }
        Log.i(TAG,response.toString())
        return when (response) {
            is Result.Success -> {
                Result.Success(response.data.toUser())
            }

            is Result.Failure -> {
                response
            }
        }
    }
}