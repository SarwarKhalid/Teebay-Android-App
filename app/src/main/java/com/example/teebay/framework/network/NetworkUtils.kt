package com.example.teebay.framework.network

import android.util.Log
import com.example.teebay.core.model.Result
import retrofit2.Response

object NetworkUtils {

    private val TAG = "NetworkUtils"

    suspend fun <T> handleApiResponse(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            Log.i(TAG,response.toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Failure(response.code(), "Empty response body")
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.Failure(response.code(), errorBody)
            }
        } catch (e: Exception) {
            Result.Failure(-1, e.localizedMessage ?: "Network error", e)
        }
    }
}