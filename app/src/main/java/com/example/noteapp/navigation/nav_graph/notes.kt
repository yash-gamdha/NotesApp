package com.example.noteapp.navigation.nav_graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusRequester
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.noteapp.features.core.presentation.undoDeleted
import com.example.noteapp.features.notes.presentation.AddEditNoteScreen
import com.example.noteapp.features.notes.presentation.NoteScreen
import com.example.noteapp.features.notes.presentation.SearchScreen
import com.example.noteapp.features.notes.presentation.ShowNoteScreen
import com.example.noteapp.navigation.Tab
import com.example.noteapp.navigation.Screen

fun NavGraphBuilder.notes(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    focusRequester: FocusRequester
) {
    navigation(
        startDestination = Screen.NoteScreen.route,
        route = Tab.Notes.route
    ) {
        // loading home screen
        composable(
            route = Screen.NoteScreen.route
        ) {
            NoteScreen(
                onEditClickListener = { noteId ->
                    navController.navigate(
                        route = "${Screen.AddEditNoteScreen.route}/$noteId"
                    )
                },
                onAddClickListener = { noteId ->
                    navController.navigate(
                        route = "${Screen.AddEditNoteScreen.route}/$noteId"
                    )
                },
                onShowNoteClickListener = { noteId ->
                    navController.navigate(
                        route = "${Screen.ShowNoteScreen.route}/$noteId"
                    )
                },
                onSearchClickListener = {
                    navController.navigate(
                        route = Screen.SearchScreen.route
                    )
                },
                snackbarHostState = snackbarHostState
            )
        }
        // navigating to add/edit screen
        composable(
            route = "${Screen.AddEditNoteScreen.route}/{noteId}",
            arguments = listOf(
                navArgument(name = "noteId") { type = NavType.IntType }
            ),
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    ),
                    initialOffsetX = { -it }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    ),
                    targetOffsetX = { -it }
                )
            }
        ) { entry ->
            entry.arguments?.getInt("noteId").let { noteId ->
                AddEditNoteScreen(
                    noteId = noteId!!,
                    navController = navController,
                    focusRequester = focusRequester
                )
            }
        }
        // navigating to show screen
        composable(
            route = "${Screen.ShowNoteScreen.route}/{noteId}",
            arguments = listOf(
                navArgument(name = "noteId") { type = NavType.IntType }
            ),
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    ),
                    initialOffsetX = { -it }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    ),
                    targetOffsetX = { -it }
                )
            }
        ) { entry ->
            entry.arguments?.getInt("noteId").let { noteId ->
                ShowNoteScreen(
                    noteId = noteId!!,
                    navController = navController,
                    onEditClickListener = {
                        navController.navigate(
                            route = "${Screen.AddEditNoteScreen.route}/$noteId"
                        )
                    },
                    snackbarHostState = snackbarHostState
                )
            }
        }
        // navigating to search screen
        composable(
            route = Screen.SearchScreen.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        ) {
            SearchScreen(
                onShowNoteClickListener = { noteId ->
                    navController.navigate(
                        route = "${Screen.ShowNoteScreen.route}/$noteId"
                    )
                },
                onEditClickListener = { noteId ->
                    navController.navigate(
                        route = "${Screen.AddEditNoteScreen.route}/$noteId"
                    )
                },
                navController = navController,
                snackbarHostState = snackbarHostState,
                focusRequester = focusRequester
            )
        }
    }
}