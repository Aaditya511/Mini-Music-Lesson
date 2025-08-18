package com.example.minimusiclesson.core.network

sealed class ApiResponse<out T> {
    data class OnApiSuccess<T>(val data: T) : ApiResponse<T>()
    data class OnApiError(val message: String) : ApiResponse<Nothing>()
}