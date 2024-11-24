package com.example.noteapp.features.notes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.features.core.presentation.MainViewModel
import com.example.noteapp.features.core.presentation.toastMsg
import com.example.noteapp.features.core.ui.theme.poppinsFontFamily
import com.example.noteapp.features.notes.presentation.components.TopAppBars

@Composable
fun AddEditNoteScreen(
    noteId: Int,
    navController: NavController,
    focusRequester: FocusRequester,
    viewModel: MainViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        if (noteId > 0) {
            viewModel.getNoteById(noteId)
        }
    }

    val context = LocalContext.current

    val textStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = poppinsFontFamily
    )

    TopAppBars.addEditBackClick = {
        navController.popBackStack()
    }

    TopAppBars.saveButtonClickListener = {
        if (viewModel.note.title.isNotBlank()) {
            viewModel.insertNote(viewModel.note)
            navController.popBackStack()
        } else {
            toastMsg(
                context = context,
                message = "Title can't be empty"
            )
        }
    }

    TopAppBars.noteId = "$noteId"
    TopAppBars.addEditToggleButton = {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LaunchedEffect(key1 = true) {
            if (noteId == -1) {
                focusRequester.requestFocus()
            }
        }

        // title text field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .focusRequester(focusRequester),
            shape = RectangleShape,
            textStyle = textStyle,
            value = viewModel.note.title,
            onValueChange = {
                viewModel.updateNoteTitle(it)
            },
            placeholder = {
                Text(
                    text = "Add Title...",
                    style = textStyle
                )
            }
        )

        // description text field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .padding(4.dp),
            shape = RectangleShape,
            textStyle = textStyle,
            value = viewModel.note.description ?: "",
            onValueChange = {
                viewModel.updateNoteDescription(it)
            },
            placeholder = {
                Text(
                    text = "Add Description (Optional)",
                    style = textStyle
                )
            },
        )
    }
}