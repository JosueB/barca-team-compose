package com.example.barcateam.network

sealed interface NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>
    data class Error(val message: String, val ex: Throwable? = null) : NetworkResult<Nothing>
}