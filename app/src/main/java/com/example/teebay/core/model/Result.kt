package com.example.teebay.core.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(
        val code: Int,
        val message: String,
        val throwable: Throwable? = null
    ) : Result<Nothing>()
}