package com.example.teebay.framework.network.datasourceimpl

import android.util.Log
import com.example.teebay.core.data.datasource.IUserDataSourceRemote
import com.example.teebay.framework.network.ApiService
import com.example.teebay.framework.network.NetworkUtils
import com.example.teebay.framework.network.request.LoginRequest
import com.example.teebay.core.model.Result
import com.example.teebay.core.model.User
import com.example.teebay.framework.network.response.toUser
import jakarta.inject.Inject

class RetrofitUserDataSource @Inject constructor(private val apiService: ApiService) :
    IUserDataSourceRemote {

    private val TAG = "RetrofitUserDatasource"

    override suspend fun getUser(email: String, password: String): Result<User> {
        Log.i(TAG, "loginUser")
        val response = NetworkUtils.handleApiResponse {
            apiService.loginUser(LoginRequest(email, password))
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