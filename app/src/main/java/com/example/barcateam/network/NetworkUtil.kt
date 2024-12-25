package com.example.barcateam.network

import retrofit2.HttpException
import retrofit2.Response

/**
 * Method that wraps most call and process them from a regular retrofit response
 * to a [NetworkResult] type
 */
suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = call.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else if (response.isSuccessful && Unit as? T != null) {
            NetworkResult.Success(Unit as T)
        } else {
            NetworkResult.Error(message = "Error Occurred during getting safe Api result, ${call.javaClass.name}, Response [$response]")
        }
    } catch (e: HttpException) {
        NetworkResult.Error(message = "Error Occurred during getting safe Api result, ${call.javaClass.name}, ERROR [$e]")
    } catch (e: Throwable) {
        NetworkResult.Error(message = "Error Occurred during getting safe Api result, ${call.javaClass.name}, ERROR [$e]")
    }
}