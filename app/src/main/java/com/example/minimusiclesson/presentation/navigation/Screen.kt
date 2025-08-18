package com.example.minimusiclesson.presentation.navigation


sealed class Screen(val route: String) {
    object HomePage : Screen("home_page")
    object DetailPage : Screen("detail_page")
}