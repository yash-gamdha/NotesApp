package com.example.noteapp.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.noteapp.features.notes.presentation.InfoScreen
import com.example.noteapp.navigation.Screen
import com.example.noteapp.navigation.Tab

fun NavGraphBuilder.about(navController: NavController) {
    navigation(
        startDestination = Screen.InfoScreen.route,
        route = Tab.About.route
    ) {
        composable(
            route = Screen.InfoScreen.route
        ) {
            InfoScreen(navController = navController)
        }
    }
}