package com.example.noteapp.features.notes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.features.notes.domain.model.Note

@Composable
fun NoteList(
    notes: List<Note>,
    onShowNoteClickListener:(Int) -> Unit,
    onEditClickListener: (Int) -> Unit,
    onUndoClickListener: () -> Unit,
    state: LazyStaggeredGridState
) {
    // loading notes
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = StaggeredGridCells.Adaptive(160.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
        state = state
    ) {
        items(notes) { note ->
            NoteCard(
                note = note,
                onShowNoteClickListener = onShowNoteClickListener,
                onEditClickListener = onEditClickListener,
                onUndoClickListener = onUndoClickListener
            )
        }
    }
}