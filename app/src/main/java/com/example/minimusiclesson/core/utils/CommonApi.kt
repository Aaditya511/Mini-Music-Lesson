package com.example.minimusiclesson.core.utils

import com.example.minimusiclesson.core.network.ApiResponse
import com.example.minimusiclesson.core.network.NetworkChecker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> callApi(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    networkChecker: NetworkChecker,
    apiCall: suspend () -> Response<T>
): ApiResponse<T> {
    return withContext(dispatcher) {
        if (!networkChecker.isInternetAvailable()) {
            return@withContext ApiResponse.OnApiError("No internet connection")
        }
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResponse.OnApiSuccess(body)
                } else {
                    ApiResponse.OnApiError("Response body is null")
                }
            } else {
                ApiResponse.OnApiError("HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            ApiResponse.OnApiError(e.localizedMessage ?: "Something went wrong")
        }
    }
}


