package com.example.noteapp.features.notes.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.features.core.presentation.MainViewModel
import com.example.noteapp.features.notes.domain.model.Note

@Composable
fun SuggestionsList(
    notes: List<Note>,
    onClickListener: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45F)
            .padding(start = 25.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes) { note ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClickListener(note.title)
                    },
                text = note.title,
                fontFamily = FontFamily.Monospace,
                fontSize = 20.sp
            )
        }
    }
}