package com.example.noteapp.features.notes.presentation.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.noteapp.features.core.presentation.MainViewModel
import com.example.noteapp.features.core.presentation.toastMsg
import com.example.noteapp.features.core.ui.theme.poppinsFontFamily
import com.example.noteapp.features.notes.domain.model.Note

@Composable
fun NoteCard(
    note: Note,
    onShowNoteClickListener: (Int) -> Unit,
    onEditClickListener: (Int) -> Unit,
    onUndoClickListener: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val clipboardManager =
        LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    // mini note card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onShowNoteClickListener(note.id) // navigating to show note screen
                    },
                    onLongPress = {
                        // coping note to the clipboard
                        clipboardManager.setPrimaryClip(
                            ClipData.newPlainText(
                                "Note copied",
                                "${note.title}\n\n${if (note.description.isNullOrBlank()) "" else note.description}"
                            )
                        )
                        // making toast
                        toastMsg(
                            context = context,
                            message = "Note copied"
                        )
                    }
                )
            },
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            if (!note.isBookmarked) {
                Spacer(modifier = Modifier.size(8.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // title text
                Text(
                    modifier = Modifier
                        .weight(8.5f),
                    text = note.title,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                if (note.isBookmarked) {
                    IconButton(
                        modifier = Modifier
                            .weight(1.5f),
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.BookmarkAdded,
                            contentDescription = "Bookmarked"
                        )
                    }
                }
            }
            if (!note.description.isNullOrBlank()) {
                // description text
                Text(
                    text = note.description,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(
                        top = if (note.description.isNullOrBlank()) 4.dp else 8.dp
                    ),
                color = MaterialTheme.colorScheme.secondary,
                thickness = 0.5.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // delete icon
                IconButton(
                    onClick = {
                        viewModel.deleteNote(note)
                        onUndoClickListener()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteSweep,
                        contentDescription = "Delete Note"
                    )
                }

                // edit icon
                IconButton(
                    onClick = { onEditClickListener(note.id) } // navigating to edit screen
                ) {
                    Icon(
                        imageVector = Icons.Default.EditNote,
                        contentDescription = "Edit Note"
                    )
                }
            }
        }
    }
}