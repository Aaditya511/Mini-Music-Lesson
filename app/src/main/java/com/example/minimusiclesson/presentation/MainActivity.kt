package com.example.minimusiclesson.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.minimusiclesson.presentation.navigation.AppNavGraph
import com.example.minimusiclesson.ui.theme.MiniMusicLessonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MiniMusicLessonTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}