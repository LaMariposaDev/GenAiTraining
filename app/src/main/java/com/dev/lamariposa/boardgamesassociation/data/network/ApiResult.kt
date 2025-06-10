package com.dev.lamariposa.boardgamesassociation.data.network

import retrofit2.HttpException
import java.io.IOException

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> Result.Error(Exception("Network Error"))
            is HttpException -> {
                val code = throwable.code()
                Result.Error(Exception("Error $code: ${throwable.message()}"))
            }
            else -> Result.Error(Exception(throwable.message))
        }
    }
}
