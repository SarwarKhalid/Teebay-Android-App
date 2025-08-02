package com.teebay.appname.core.data.datasource

interface ITokenDataSource {

    fun saveToken(token: String)

    suspend fun getToken(): String?

    suspend fun refreshToken()
}