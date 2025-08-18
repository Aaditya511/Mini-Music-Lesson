package com.example.minimusiclesson.presentation.common

sealed class CommonUiStates<out T> {
    object InitialState : CommonUiStates<Nothing>()
    object Loading : CommonUiStates<Nothing>()
    data class Success<T>(val data: T) : CommonUiStates<T>()
    data class Error(val message: String?) : CommonUiStates<Nothing>()
}