package com.example.barcateam.network

/**
 * Reusable Sealed class to know status of a network result
 */
sealed interface NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>
    data class Error(val message: String, val ex: Throwable? = null) : NetworkResult<Nothing>
}