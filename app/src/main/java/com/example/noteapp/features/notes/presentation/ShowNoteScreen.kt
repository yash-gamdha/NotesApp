package com.example.noteapp.features.notes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.features.core.presentation.MainViewModel
import com.example.noteapp.features.core.presentation.undoDeleted
import com.example.noteapp.features.core.ui.theme.poppinsFontFamily
import com.example.noteapp.features.core.ui.theme.ubuntuFontFamily
import com.example.noteapp.features.notes.presentation.components.TopAppBars
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ShowNoteScreen(
    noteId: Int,
    navController: NavController,
    onEditClickListener: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val textStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = poppinsFontFamily
    )

    LaunchedEffect(key1 = true) {
        if (noteId > 0) {
            viewModel.getNoteById(noteId)
        }
    }

    TopAppBars.titleComposable = {
        Text(
            text = viewModel.note.title,
            fontFamily = ubuntuFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }

    TopAppBars.showScreenBackButton = {
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
    }

    TopAppBars.showScreenToggleButton = {
        IconToggleButton(
            checked = viewModel.note.isBookmarked,
            onCheckedChange = {
                viewModel.updateNoteBookMarked(it)
                viewModel.updateNote(
                    viewModel.note.copy(
                        isBookmarked = it
                    )
                )
            }
        ) {
            Icon(
                imageVector = if (viewModel.note.isBookmarked) Icons.Filled.BookmarkAdded
                else Icons.Outlined.BookmarkAdd,
                contentDescription = if (viewModel.note.isBookmarked) "Added to favourite"
                else "Add to favourite"
            )
        }
    }

    TopAppBars.deleteButton = {
        IconButton(
            onClick = {
                viewModel.deleteNote(viewModel.note)
                undoDeleted(
                    scope = scope,
                    viewModel = viewModel,
                    snackbarHostState = snackbarHostState
                )
                scope.launch {
                    delay(5000)
                    navController.popBackStack()
                }
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.DeleteSweep,
                contentDescription = "Delete Note"
            )
        }
    }

    TopAppBars.editButton = {
        IconButton(
            onClick = { onEditClickListener(viewModel.note.id) }
        ) {
            Icon(
                imageVector = Icons.Default.EditNote,
                contentDescription = "Edit Note"
            )
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = viewModel.note.description ?: "",
                style = textStyle
            )
        }
    }

}