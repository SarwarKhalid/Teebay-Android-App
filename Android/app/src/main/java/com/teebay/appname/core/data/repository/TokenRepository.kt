package com.teebay.appname.core.data.repository

import com.teebay.appname.core.data.datasource.ITokenDataSource
import javax.inject.Inject

class TokenRepository @Inject constructor(private val tokenDatasource: ITokenDataSource) {

    fun saveToken(token: String) {
        tokenDatasource.saveToken(token)
    }

    suspend fun getToken(): String? {
        return tokenDatasource.getToken()
    }

    suspend fun refreshToken() {
        tokenDatasource.refreshToken()
    }
}