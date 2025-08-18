package com.example.minimusiclesson.presentation.common

sealed class CommonScreenEvents {
    data class NavigationEvent<T>(
        val route: String,
        val data: T? = null
    ) : CommonScreenEvents()

    data class ShowSnackbarEvent(val message: String) : CommonScreenEvents()

}