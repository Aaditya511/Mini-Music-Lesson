package com.example.minimusiclesson.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.minimusiclesson.data.model.Lesson
import com.example.minimusiclesson.presentation.details.DetailPageScreen
import com.example.minimusiclesson.presentation.home.HomePageScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomePage.route
    ) {

        composable(Screen.HomePage.route) {
            HomePageScreen(navController)
        }

        composable(Screen.DetailPage.route) {
            val lesson = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Lesson>("lesson")
            DetailPageScreen(navController, lesson)
        }
    }
}

