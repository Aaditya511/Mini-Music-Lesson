package com.example.minimusiclesson.presentation.common

sealed class ScreenEvents {
    data class NavigationEvent<T>(
        val route: String,
        val data: T? = null
    ) : ScreenEvents()

    data class ShowSnackbarEvent(val message: String) : ScreenEvents()

}