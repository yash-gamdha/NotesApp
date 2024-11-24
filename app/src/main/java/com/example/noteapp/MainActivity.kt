package com.example.noteapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.features.core.ui.theme.NoteAppYTTheme
import com.example.noteapp.features.notes.presentation.components.FABs
import com.example.noteapp.features.notes.presentation.components.TopAppBars
import com.example.noteapp.features.notes.presentation.components.BottomNavBar
import com.example.noteapp.navigation.Screen
import com.example.noteapp.navigation.Tab
import com.example.noteapp.navigation.nav_graph.about
import com.example.noteapp.navigation.nav_graph.favourites
import com.example.noteapp.navigation.nav_graph.notes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            NoteAppYTTheme {
                // navigation related
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val isBottomNavVisible = rememberSaveable(navBackStackEntry) {
                    navBackStackEntry?.destination?.route == Screen.NoteScreen.route ||
                            navBackStackEntry?.destination?.route == Screen.FavouriteScreen.route ||
                            navBackStackEntry?.destination?.route == Screen.InfoScreen.route
                }

                // initializing scroll behaviours of different screens
                TopAppBars.scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                TopAppBars.favScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                TopAppBars.infoScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                TopAppBars.searchScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                TopAppBars.addEditScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

                var selectedTopAppBar by remember {
                    mutableStateOf(TopAppBars.mainScreenTopAppBar)
                }

                var nestedScrollConnection by remember {
                    mutableStateOf(TopAppBars.scrollBehavior.nestedScrollConnection)
                }

                var isMainScreen by remember {
                    mutableStateOf(true)
                }

                // setting top app bar based on selected screen
                when (navBackStackEntry?.destination?.route) {
                    Screen.NoteScreen.route -> {
                        selectedTopAppBar = TopAppBars.mainScreenTopAppBar
                        nestedScrollConnection = TopAppBars.scrollBehavior.nestedScrollConnection
                        isMainScreen = true
                    }

                    Screen.FavouriteScreen.route -> {
                        selectedTopAppBar = TopAppBars.favouriteScreenTopAppBar
                        nestedScrollConnection = TopAppBars.favScrollBehavior.nestedScrollConnection
                        isMainScreen = false
                    }

                    Screen.InfoScreen.route -> {
                        selectedTopAppBar = TopAppBars.infoScreenTopAppBar
                        nestedScrollConnection =
                            TopAppBars.infoScrollBehavior.nestedScrollConnection
                        isMainScreen = false
                    }

                    Screen.SearchScreen.route -> {
                        selectedTopAppBar = TopAppBars.searchScreenTopAppBar
                        nestedScrollConnection =
                            TopAppBars.searchScrollBehavior.nestedScrollConnection
                        isMainScreen = false
                    }
                    // screens with variables
                    "${Screen.ShowNoteScreen.route}/{noteId}" -> {
                        TopAppBars.showScrollBehavior =
                            TopAppBarDefaults.enterAlwaysScrollBehavior() // is initialized here to fully show top app bar when different notes are viewed
                        selectedTopAppBar = TopAppBars.showScreenTopAppBar
                        nestedScrollConnection =
                            TopAppBars.showScrollBehavior.nestedScrollConnection
                        isMainScreen = false
                    }

                    "${Screen.AddEditNoteScreen.route}/{noteId}" -> {
                        selectedTopAppBar = TopAppBars.addEditScreenTopAppBar
                        nestedScrollConnection =
                            TopAppBars.addEditScrollBehavior.nestedScrollConnection
                        isMainScreen = false
                    }
                }

                val snackbarHostState = remember { SnackbarHostState() }
                val focusRequester = FocusRequester()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(nestedScrollConnection),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    topBar = selectedTopAppBar,
                    bottomBar = {
                        if (isBottomNavVisible) {
                            BottomNavBar(navController)
                        }
                    },
                    floatingActionButton = {
                        if (isMainScreen) {
                            FABs.MsFAB()
                        }
                    }
                ) { padding ->
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        navController = navController,
                        startDestination = Tab.Notes.route
                    ) {
                        notes(
                            navController = navController,
                            snackbarHostState = snackbarHostState,
                            focusRequester = focusRequester
                        )
                        favourites(
                            navController = navController,
                            snackbarHostState = snackbarHostState
                        )
                        about(navController = navController)
                    }
                }
            }
        }
    }
}