package com.example.minimusiclesson.presentation.common

sealed class UIState<out T> {
    object InitialState : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val message: String?) : UIState<Nothing>()
}