package com.teebay.appname.core.domain

import com.teebay.appname.core.data.repository.TokenRepository
import javax.inject.Inject

class TokenUseCase @Inject constructor(private val tokenRepository: TokenRepository) {

    fun saveToken(token: String) {
        tokenRepository.saveToken(token)
    }

    suspend fun getToken(): String? {
        return tokenRepository.getToken()
    }

    suspend fun refreshToken() {
        tokenRepository.refreshToken()
    }
}