package com.example.noteapp.features.notes.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.noteapp.features.core.presentation.EmptyScreenList
import com.example.noteapp.features.core.presentation.MainViewModel
import com.example.noteapp.features.core.presentation.undoDeleted
import com.example.noteapp.features.notes.presentation.components.FABs
import com.example.noteapp.features.notes.presentation.components.LoadingAndErrorScreen
import com.example.noteapp.features.notes.presentation.components.NoteList
import com.example.noteapp.features.notes.presentation.components.TopAppBars
import com.example.noteapp.util.Response

@Composable
fun NoteScreen(
    onShowNoteClickListener: (Int) -> Unit,
    onEditClickListener: (Int) -> Unit,
    onAddClickListener: (Int) -> Unit,
    onSearchClickListener: () -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    TopAppBars.onSearchClickListener = onSearchClickListener
    FABs.onAddClickListener = onAddClickListener

    val response by viewModel.response.collectAsStateWithLifecycle()

    val gridScrollState = rememberLazyStaggeredGridState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // screen animation
        AnimatedContent(
            modifier = Modifier
                .fillMaxSize(),
            targetState = response,
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
                is Response.Loading -> { // displaying loading msg
                    LoadingAndErrorScreen(label = "Loading...")
                }

                is Response.Success -> {
                    val notes = result.data // fetching notes
                    if (notes.isEmpty()) { // displaying empty screen list
                        EmptyScreenList()
                    } else { // displaying notes
                        NoteList(
                            notes = notes,
                            onEditClickListener = onEditClickListener,
                            onUndoClickListener = { // showing snack bar to undo deleted note
                                undoDeleted(
                                    scope = scope,
                                    viewModel = viewModel,
                                    snackbarHostState = snackbarHostState
                                )
                            },
                            onShowNoteClickListener = onShowNoteClickListener,
                            state = gridScrollState
                        )
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