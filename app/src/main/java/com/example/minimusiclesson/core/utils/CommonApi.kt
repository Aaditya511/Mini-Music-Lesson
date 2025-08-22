package com.example.minimusiclesson.core.utils

import com.example.minimusiclesson.core.network.ApiResponse
import com.example.minimusiclesson.core.network.NetworkUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> callApi(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    networkChecker: NetworkUtils,
    apiCall: suspend () -> Response<T>
): ApiResponse<T> {
    return withContext(dispatcher) {
        if (!networkChecker.isInternetAvailable()) {
            return@withContext ApiResponse.Error("No internet connection")
        }
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResponse.Success(body)
                } else {
                    ApiResponse.Error("Response body is null")
                }
            } else {
                ApiResponse.Error("HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            ApiResponse.Error(e.localizedMessage ?: "Something went wrong")
        }
    }
}


