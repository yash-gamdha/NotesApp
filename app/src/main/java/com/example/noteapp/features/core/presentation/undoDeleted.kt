package com.example.noteapp.features.core.presentation

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// snackbar for undo
fun undoDeleted(
    scope: CoroutineScope,
    viewModel: MainViewModel,
    snackbarHostState: SnackbarHostState
) {
    scope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()
        val result = snackbarHostState
            .showSnackbar(
                message = "Note Deleted",
                actionLabel = "UNDO",
                duration = SnackbarDuration.Short
            )

        when (result) {
            SnackbarResult.ActionPerformed -> {
                viewModel.undoDeletedNote()
            }
            SnackbarResult.Dismissed -> {
                viewModel.deletedNote = null
            }
        }
    }
}