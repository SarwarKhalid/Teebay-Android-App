package com.teebay.appname.core.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(
        val code: Int? = null,
        val message: String? = null,
        val throwable: Throwable? = null
    ) : Result<Nothing>()
}