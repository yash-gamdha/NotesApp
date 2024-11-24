package com.example.noteapp.features.notes.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

object FABs {
    lateinit var onAddClickListener: (Int) -> Unit

    @Composable
    fun MsFAB() = FloatingActionButton(
        onClick = { onAddClickListener(-1) }, // navigating to add screen
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Note"
        )
    }
}