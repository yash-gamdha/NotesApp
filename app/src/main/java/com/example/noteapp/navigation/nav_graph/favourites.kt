package com.example.noteapp.navigation.nav_graph

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.noteapp.features.favourites.presentation.FavouriteScreen
import com.example.noteapp.navigation.Screen
import com.example.noteapp.navigation.Tab

fun NavGraphBuilder.favourites(navController: NavController, snackbarHostState: SnackbarHostState) {
    navigation(
        startDestination = Screen.FavouriteScreen.route,
        route = Tab.Favourites.route
    ) {
        composable(
            route = Screen.FavouriteScreen.route
        ) {
            FavouriteScreen(
                onEditClickListener = { noteId ->
                    navController.navigate(
                        route = "${Screen.AddEditNoteScreen.route}/$noteId"
                    )
                },
                navController = navController,
                onShowNoteClickListener = { noteId ->
                    navController.navigate(
                        route = "${Screen.ShowNoteScreen.route}/$noteId"
                    )
                },
                snackbarHostState = snackbarHostState
            )
        }
    }
}