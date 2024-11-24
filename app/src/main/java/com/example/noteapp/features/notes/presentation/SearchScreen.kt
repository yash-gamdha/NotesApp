package com.example.noteapp.features.notes.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.noteapp.features.core.presentation.MainViewModel
import com.example.noteapp.features.core.presentation.toastMsg
import com.example.noteapp.features.core.presentation.undoDeleted
import com.example.noteapp.features.notes.presentation.components.LoadingAndErrorScreen
import com.example.noteapp.features.notes.presentation.components.NoteList
import com.example.noteapp.features.notes.presentation.components.SuggestionsList
import com.example.noteapp.features.notes.presentation.components.TopAppBars
import com.example.noteapp.util.Response

@Composable
fun SearchScreen(
    onShowNoteClickListener: (Int) -> Unit,
    onEditClickListener: (Int) -> Unit,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    focusRequester: FocusRequester,
    viewModel: MainViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    TopAppBars.searchData = remember {
        mutableStateOf("")
    }

    TopAppBars.wantsToSearch = remember {
        mutableStateOf(false)
    }

    TopAppBars.searchScreenBackClick = {
        navController.popBackStack()
    }

    TopAppBars.doToast = {
        toastMsg(
            context = context,
            message = "Search field can't be empty"
        )
    }

    TopAppBars.search = {
        viewModel.search("%${TopAppBars.searchData.value}%")
        TopAppBars.wantsToSearch.value = true
        keyboardController?.hide()
    }

    TopAppBars.liveSearch = {
        viewModel.search("%${TopAppBars.searchData.value}%")
    }

    TopAppBars.focusRequester = focusRequester

    val searchResponse by viewModel.searchResponse.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    val gridScrollState = rememberLazyStaggeredGridState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LaunchedEffect(key1 = true) {
            TopAppBars.focusRequester.requestFocus()
        }
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 8.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
        AnimatedContent(
            modifier = Modifier
                .fillMaxSize(),
            targetState = searchResponse,
            label = "Animated content",
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) togetherWith fadeOut(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
        ) { result ->
            when (result) {
                is Response.Loading -> {
                    LoadingAndErrorScreen("Search Notes")
                }

                is Response.Success -> {
                    val notes = result.data
                    if (notes.isEmpty()) {
                        LoadingAndErrorScreen("No notes found with the given title")
                    } else {
                        if (TopAppBars.wantsToSearch.value) { // loading notes
                            NoteList(
                                notes = notes,
                                onEditClickListener = onEditClickListener,
                                onShowNoteClickListener = onShowNoteClickListener,
                                onUndoClickListener = {
                                    undoDeleted(
                                        scope = scope,
                                        viewModel = viewModel,
                                        snackbarHostState = snackbarHostState
                                    )
                                },
                                state = gridScrollState
                            )
                        } else { // loading search suggestions
                            SuggestionsList(
                                notes = notes,
                                onClickListener = { title ->
                                    viewModel.search("%${title}%")
                                    TopAppBars.wantsToSearch.value = true
                                    keyboardController?.hide()
                                }
                            )
                        }
                    }
                }

                is Response.Error -> { // displaying error msg
                    val error = result.error.message ?: "Something went wrong"
                    LoadingAndErrorScreen(label = error)
                }

                else -> Unit
            }
        }
    }

}